import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CreditCardService } from '../../services/CreditCardService/credit-card.service';
import { SecurityService } from '../../security/security.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router'; 

@Component({
  selector: 'app-credit-card-form',
  templateUrl: './credit-card-form.component.html'
})
export class CreditCardFormComponent {
  creditCardForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private creditCardService: CreditCardService,
    private securityService: SecurityService, // Inject SecurityService
    private toastr: ToastrService,
    private router: Router
  ) {
    // Access profile data from SecurityService
    const customerId = this.securityService.profile?.id || null; // Ensure it handles undefined profile

    this.creditCardForm = this.fb.group({
      cardId: [new Date().getTime().toString()],
      cardHolderName: ['', Validators.required],
      cardNum: ['', Validators.required],
      expirationMonth: ['', Validators.required],
      expirationYear: ['', Validators.required],
      cvv: ['', Validators.required],
      customerId: [customerId, Validators.required], // Use the retrieved customerId
    });
  }


  ngOnInit(): void {
    const customerId = this.securityService.profile?.id || null;
  
    // Ensure customerId is not null before proceeding
    if (customerId) {
      this.creditCardForm.patchValue({ customerId });
  
      // Fetch saved credit card data
      this.creditCardService.getCreditCardByCustomerId(customerId).subscribe({
        next: (data) => {
          if (data) {
            this.creditCardForm.patchValue(data);
          }
        },
        error: (error) => {
          console.error('Error fetching credit card data:', error);
          this.toastr.error('Failed to load saved card data.', 'Error');
        },
      });
    } else {
      console.error('Customer ID is not available');
      this.toastr.error('Unable to load card data. Please try again later.', 'Error');
    }
  }
  
  onSubmit() {
    if (this.creditCardForm.valid) {
      this.creditCardService.createCreditCard(this.creditCardForm.value)
        .subscribe({
          next: (response) => {
            console.log('Card saved successfully');
            this.toastr.success('Card saved successfully!', 'Success');
          },
          error: (error) => {
            console.error('Error saving card:', error);
            this.toastr.error('Failed to save card. Please try again.', 'Error');
          }
        });
    } else {
      this.toastr.warning('Please fill all required fields.', 'Warning');
    }
  }

  onHome() {
    this.router.navigateByUrl("home"); // Navigate to "home"
  }
}
