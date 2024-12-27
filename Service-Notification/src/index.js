require('dotenv').config();
const { Kafka } = require('kafkajs');
const nodemailer = require('nodemailer');

// Kafka Configuration
const kafka = new Kafka({
    clientId: 'notification-microservice',
    brokers: [process.env.KAFKA_BROKER]
});
const consumer = kafka.consumer({ groupId: process.env.KAFKA_GROUP_ID });

// Email Service Configuration
const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: process.env.GMAIL_USER,
        pass: process.env.GMAIL_PASS
    }
});

// Email Sending Function
const sendEmail = async (to, subject, htmlContent) => {
    try {
        await transporter.sendMail({
            from: `"FPL E-Commerce" <${process.env.GMAIL_USER}>`,
            to,
            subject,
            html: htmlContent
        });
        console.log(`Email sent to ${to} with subject: ${subject}`);
    } catch (error) {
        console.error(`Failed to send email to ${to}:`, error.message);
    }
};

// Message Handlers
const handlePaymentEvent = async (message) => {
    try {
        const rawMessage = message.value?.toString();
        if (!rawMessage) {
            console.error("Received empty or null message in payments-events topic.");
            return;
        }

        const event = JSON.parse(rawMessage);
        if (event.paymentId) {
            // Payment Successful
            const subject = `Payment Successful - Order #${event.orderId} Confirmation`;
            const htmlContent = `
                <html>
                    <body style="font-family: Arial, sans-serif; color: #333;">
                        <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;">
                            <h2 style="color: #4CAF50;">Payment Successful - Order #${event.orderId} Confirmation</h2>
                            <p><strong>Dear ${event.firstname} ${event.lastname},</strong></p>
                            <p>We are pleased to inform you that your payment has been successfully processed, and your order has been confirmed.</p>
                            <p><strong>Order Details:</strong></p>
                            <table style="width: 100%; margin-top: 10px; border-collapse: collapse;">
                                <tr>
                                    <td style="padding: 8px; border: 1px solid #ddd;"><strong>Order ID:</strong></td>
                                    <td style="padding: 8px; border: 1px solid #ddd;">#${event.orderId}</td>
                                </tr>
                                <tr>
                                    <td style="padding: 8px; border: 1px solid #ddd;"><strong>Payment ID:</strong></td>
                                    <td style="padding: 8px; border: 1px solid #ddd;">${event.paymentId}</td>
                                </tr>
                                <tr>
                                    <td style="padding: 8px; border: 1px solid #ddd;"><strong>Shipping Address:</strong></td>
                                    <td style="padding: 8px; border: 1px solid #ddd;">${event.shippingAddress}</td>
                                </tr>
                            </table>
                            <p>Your order is now in our system, and we will begin preparing it for shipment. You will receive an update with tracking details once your items are on their way.</p>
                            <p><strong>Thank you for choosing us!</strong></p>
                            <p>Best regards,<br>The FPL E-Commerce Team</p>
                            <p>If you did not make this order or believe there is an issue with your purchase, please contact us immediately at <a href="mailto:support@example.com">support@example.com</a>.</p>
                        </div>
                    </body>
                </html>
            `;
            await sendEmail(event.customerEmailAddress, subject, htmlContent);
        } else {
            // Payment Failed
            const subject = `Payment Failed - Order #${event.orderId}`;
            const htmlContent = `
                <html>
                    <body style="font-family: Arial, sans-serif; color: #333;">
                        <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;">
                            <h2 style="color: #FF6347;">Payment Failed - Order #${event.orderId}</h2>
                            <p><strong>Dear ${event.firstname} ${event.lastname},</strong></p>
                            <p>We regret to inform you that your payment for Order ID: #${event.orderId} has failed.</p>
                            <p>Please try again or contact our support team if you need assistance.</p>
                            <p>Thank you for your understanding.</p>
                            <p>Best regards,<br>The FPL E-Commerce Team</p>
                            <p>If you have any questions, feel free to contact us at <a href="mailto:support@example.com">support@example.com</a>.</p>
                        </div>
                    </body>
                </html>
            `;
            await sendEmail(event.customerEmailAddress, subject, htmlContent);
        }
    } catch (error) {
        console.error("Error processing payments-events message:", error.message);
    }
};

const handleShipmentEvent = async (message) => {
  try {
      const rawMessage = message.value?.toString();
      if (!rawMessage) {
          console.error("Received empty or null message in shipments-events topic.");
          return;
      }

      const event = JSON.parse(rawMessage);
      const subject = `Shipment Confirmation - Order #${event.orderId}`;
      const htmlContent = `
          <html>
              <body style="font-family: Arial, sans-serif; color: #333;">
                  <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;">
                      <h2 style="color: #4CAF50;">Shipment Confirmation - Order #${event.orderId}</h2>
                      <p><strong>Dear ${event.firstname} ${event.lastname},</strong></p>
                      <p>We are pleased to inform you that your order with the ID #${event.orderId} has been successfully shipped.</p>
                      <p><strong>Shipment Details:</strong></p>
                      <table style="width: 100%; margin-top: 10px; border-collapse: collapse;">
                          <tr>
                              <td style="padding: 8px; border: 1px solid #ddd;"><strong>Shipping Address:</strong></td>
                              <td style="padding: 8px; border: 1px solid #ddd;">${event.shippingAddress}</td>
                          </tr>
                          <tr>
                              <td style="padding: 8px; border: 1px solid #ddd;"><strong>Originating Address:</strong></td>
                              <td style="padding: 8px; border: 1px solid #ddd;">${event.originatingAddress}</td>
                          </tr>
                          <tr>
                              <td style="padding: 8px; border: 1px solid #ddd;"><strong>Shipment Date:</strong></td>
                              <td style="padding: 8px; border: 1px solid #ddd;">${event.shipmentInitDate}</td>
                          </tr>
                          <tr>
                              <td style="padding: 8px; border: 1px solid #ddd;"><strong>Expected Delivery Date:</strong></td>
                              <td style="padding: 8px; border: 1px solid #ddd;">${event.deliveryExpectedDate}</td>
                          </tr>
                          <tr>
                              <td style="padding: 8px; border: 1px solid #ddd;"><strong>Tracking Serial Number:</strong></td>
                              <td style="padding: 8px; border: 1px solid #ddd;">${event.trackingNum}</td>
                          </tr>
                      </table>
                      <p>Your order is now in transit. You will receive a further update once the shipment is on its way.</p>
                      <p><strong>Thank you for choosing us!</strong></p>
                      <p>Best regards,<br>The FPL E-Commerce Team</p>
                      <p>If you did not make this order or believe there is an issue with your purchase, please contact us immediately at <a href="mailto:support@example.com">support@example.com</a>.</p>
                  </div>
              </body>
          </html>
      `;
      await sendEmail(event.customerEmailAddress, subject, htmlContent);
  } catch (error) {
      console.error("Error processing shipments-events message:", error.message);
  }
};


// Kafka Consumer Setup
const run = async () => {
    try {
        await consumer.connect();
        await consumer.subscribe({ topic: process.env.KAFKA_PAYMENTS_TOPIC, fromBeginning: true });
        await consumer.subscribe({ topic: process.env.KAFKA_SHIPMENTS_TOPIC, fromBeginning: true });

        console.log('Kafka consumer connected and listening for events...');

        await consumer.run({
            eachMessage: async ({ topic, partition, message }) => {
                console.log(`Received message from topic ${topic}:`, message.value?.toString() || "No value");
                if (topic === process.env.KAFKA_PAYMENTS_TOPIC) {
                    await handlePaymentEvent(message);
                } else if (topic === process.env.KAFKA_SHIPMENTS_TOPIC) {
                    await handleShipmentEvent(message);
                }
            }
        });
    } catch (error) {
        console.error('Error in Kafka consumer:', error.message);
    }
};

run().catch(error => {
    console.error('Failed to start the Kafka consumer:', error.message);
});
