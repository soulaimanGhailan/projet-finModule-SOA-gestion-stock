import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { SecurityService } from '../../security/security.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-order-confirmation',
  templateUrl: './order-confirmation.component.html'
})
export class OrderConfirmationComponent implements OnInit {
  cartId: string = '';
  totalAmount: number = 0;
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private store: Store<any>,
    private securityService: SecurityService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.cartId = params['cartId'] || '';
      this.loadCartDetails();
    });
  }

  loadCartDetails() {
    this.store.select(state => state.shoppingCartState)
      .subscribe(cartState => {
        if (cartState.shoppingCart) {
          this.totalAmount = this.calculateTotal(cartState.shoppingCart.items);
        }
      });
  }

  calculateTotal(items: any[]): number {
    return items.reduce((total, item) => total + (item.price * item.quantity), 0);
  }

  backToCart() {
    this.router.navigate(['/cart']);
  }

  confirmOrder() {
    const orderData = {
      orderId: `ORD-${new Date().getTime()}`,
      customerId: this.securityService.profile?.id,
      amount: this.totalAmount,
      items: [] // Add cart items here
    };
    
    this.router.navigate(['/payment'], {
      queryParams: {
        orderId: orderData.orderId,
        amount: this.totalAmount
      }
    });
  }
}