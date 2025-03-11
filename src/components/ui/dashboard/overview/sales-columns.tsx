'use client';

import { currencyFormatter } from '@/lib/utils';
import { ColumnDef } from '@tanstack/react-table';
import { format } from 'date-fns';

export type Sale = {
  id: string;
  date: Date;
  amount: number;
};

export const sales: Sale[] = [
  {
    id: '1',
    amount: 455.5,
    date: new Date('2025-03-11'),
  },
  {
    id: '2',
    amount: 1242.8,
    date: new Date('2025-03-11'),
  },
  {
    id: '3',
    amount: 543.45,
    date: new Date('2025-03-11'),
  },
];

export const columns: ColumnDef<Sale>[] = [
  {
    accessorKey: 'id',
    header: 'ID',
  },
  {
    accessorKey: 'date',
    header: 'Date',
    cell: ({ row }) => {
      const date = new Date(row.getValue('date'));
      const formatted = format(date, 'P');

      return formatted;
    },
  },
  {
    accessorKey: 'amount',
    header: () => <div className="text-right">Total Amount</div>,
    cell: ({ row }) => {
      const amount = parseFloat(row.getValue('amount'));
      const formatted = currencyFormatter.format(amount);

      return <div className="text-right font-medium">{formatted}</div>;
    },
  },
];
