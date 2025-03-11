'use client';

import { currencyFormatter } from '@/lib/utils';
import { ColumnDef } from '@tanstack/react-table';
import { format } from 'date-fns';

export type Sale = {
  id: string;
  date: Date;
  amount: number;
};

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
