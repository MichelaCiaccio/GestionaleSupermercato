'use client';

import { ColumnDef } from '@tanstack/react-table';
import { format } from 'date-fns';

const LOW_STOCK_THRESHOLD = 20;
const MEDIUM_STOCK_THRESHOLD = 40;

export type OverviewProduct = {
  name: string;
  quantity: number;
  arrivalDate: Date;
  expiryDate: Date;
};

export const columns: ColumnDef<OverviewProduct>[] = [
  {
    id: 'lowStock',
    header: () => <div className="w-2" />,
    cell: ({ row }) => {
      const quantity = parseInt(row.getValue('quantity'));

      if (quantity <= LOW_STOCK_THRESHOLD) {
        return (
          <span className="bg-destructive ml-auto block h-2 w-2 rounded-full" />
        );
      }

      if (quantity <= MEDIUM_STOCK_THRESHOLD) {
        return (
          <span className="ml-auto block h-2 w-2 rounded-full bg-yellow-500 dark:bg-yellow-600" />
        );
      }

      return null;
    },
  },
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
    cell: ({ row }) => {
      return <div className="text-right">{row.getValue('quantity')}</div>;
    },
  },
];
