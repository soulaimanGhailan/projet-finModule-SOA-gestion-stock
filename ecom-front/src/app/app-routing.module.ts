import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {ShoppingCartComponent} from "./components/shopping-cart/shopping-cart.component";
import {ProductDetailsComponent} from "./components/product-details/product-details.component";
import {ContactComponent} from "./components/contact/contact.component";
import {SearchedProductsListComponent} from "./components/searched-products-list/searched-products-list.component";
import {AdminDashboardComponent} from "./components/admin-dashboard/admin-dashboard.component";
import {AddProductComponent} from "./components/add-product/add-product.component";
import {EditProductComponent} from "./components/edit-product/edit-product.component";
import {AuthGuard} from "./security/guards/sec.guard";
import { CreditCardFormComponent } from './components/credit-card-form/credit-card-form.component';
import { OrderConfirmationComponent } from './components/order-confirmation/order-confirmation.component';

const routes: Routes = [
  {path : "cart" , component:ShoppingCartComponent , canActivate:[AuthGuard]  , data : {roles : ['USER' , 'ADMIN']}},
  {path : "product-details" , component:ProductDetailsComponent },
  {path : "searched-products" , component:SearchedProductsListComponent},
  {path : "contact" , component:ContactComponent },
  {path : "admin" , component:AdminDashboardComponent , canActivate:[AuthGuard]  , data : {roles : ['ADMIN']}},
  {path : "addProduct" , component:AddProductComponent ,canActivate:[AuthGuard]  , data : {roles : ['ADMIN']}},
  {path : "edit-product" , component:EditProductComponent , canActivate:[AuthGuard]  , data : {roles : ['ADMIN']}},
  {path : "home" , component:HomeComponent},
  {path : "" , component:HomeComponent},
  { path: 'credit-card-form', component: CreditCardFormComponent },
  { path: 'order-confirmation', component: OrderConfirmationComponent },
  { path: 'payment', component: CreditCardFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
