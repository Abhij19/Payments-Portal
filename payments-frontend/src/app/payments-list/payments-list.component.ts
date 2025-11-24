import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule, DatePipe } from '@angular/common';
import { Payment, PaymentService } from '../services/payment.service';

@Component({
  selector: 'app-payments-list',
  standalone: true,
  imports: [CommonModule, DatePipe],   // ✅ FIX: ngIf, ngFor, date pipe
  templateUrl: './payments-list.component.html',
  styleUrls: ['./payments-list.component.css']
})
export class PaymentsListComponent implements OnInit {

  payments: Payment[] = [];
  loading = true;

  constructor(
    private paymentService: PaymentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadPayments();
  }

  loadPayments() {
    this.paymentService.list().subscribe({
      next: (data: Payment[]) => {
        this.payments = data;
        this.loading = false;
      },
      error: () => {
        alert('Failed to load payments');
        this.loading = false;
      }
    });
  }

  onAdd() {
    this.router.navigate(['/create']);
  }

  onEdit(id: number | undefined) {
    if (id) this.router.navigate(['/edit', id]);
  }

  onDelete(id: number | undefined) {
    if (!id) return;
    if (!confirm('Are you sure?')) return;
    this.paymentService.delete(id).subscribe(() => this.loadPayments());
  }
}
