'use client';

import { ColumnDef } from '@tanstack/react-table';
import { format } from 'date-fns';

export type Product = {
  name: string;
  quantity: number;
  arrivalDate: Date;
  expiryDate: Date;
};

export const columns: ColumnDef<Product>[] = [
  {
    accessorKey: 'name',
    header: 'Name',
  },
  {
    accessorKey: 'arrivalDate',
    header: 'Arrival Date',
    cell: ({ row }) => {
      const date = new Date(row.getValue('arrivalDate'));
      const formatted = format(date, 'P');

      return formatted;
    },
  },
  {
    accessorKey: 'expiryDate',
    header: 'Expiry Date',
    cell: ({ row }) => {
      const date = new Date(row.getValue('expiryDate'));
      const formatted = format(date, 'P');

      return formatted;
    },
  },
  {
    accessorKey: 'quantity',
    header: () => <div className="text-right">Quantity</div>,
  },
];
