import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Payment, PaymentService } from '../services/payment.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-payment-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule], 
  templateUrl: './payment-form.component.html',
  styleUrls: ['./payment-form.component.css']
})
export class PaymentFormComponent implements OnInit {

  model: Payment = {
    amount: 0,
    currency: 'USD',
    clientRequestId: ''
  };

  isEdit = false;

  constructor(
    private route: ActivatedRoute,
    private paymentService: PaymentService,
    private router: Router
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.paymentService.get(+id).subscribe({
        next: (p: Payment) => this.model = p,
        error: () => alert("Payment not found")
      });
    }
  }

  save() {
    if (this.isEdit) {
      this.paymentService.update(
        this.model.id!,
        this.model.amount,
        this.model.currency,
        this.model.clientRequestId
      ).subscribe(() => this.router.navigate(['/']));
    } else {
      this.paymentService.create(
        this.model.amount,
        this.model.currency
      ).subscribe(() => this.router.navigate(['/']));
    }
  }
}
