'use client';

import { format } from 'date-fns';
import { ColumnsBuilder } from '../../data-table';
import { currencyFormatter } from '@/lib/utils';
import { Skeleton } from '../../skeleton';

export type Sale = {
  id: number;
  date: Date;
  amount: number;
};

export type SaleSkeleton = {
  [P in keyof Sale]: number;
};

const [columns, skeletonColumns] = new ColumnsBuilder<Sale, SaleSkeleton>()
  .addColumn(
    {
      accessorKey: 'id',
      header: 'ID',
    },
    {
      accessorKey: 'id',
      header: 'ID',
      cell: ({ row }) => (
        <Skeleton className="h-5" style={{ width: row.original.id }} />
      ),
    }
  )
  .addColumn(
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
      accessorKey: 'date',
      header: 'Date',
      cell: ({ row }) => (
        <Skeleton className="h-5" style={{ width: row.original.date }} />
      ),
    }
  )
  .addColumn(
    {
      accessorKey: 'amount',
      header: () => <div className="text-right">Total Amount</div>,
      cell: ({ row }) => {
        const amount = parseFloat(row.getValue('amount'));
        const formatted = currencyFormatter.format(amount);

        return <div className="text-right font-medium">{formatted}</div>;
      },
    },
    {
      accessorKey: 'amount',
      header: () => <div className="text-right">Total Amount</div>,
      cell: ({ row }) => (
        <Skeleton className="h-5" style={{ width: row.original.amount }} />
      ),
    }
  )
  .build();

export { columns, skeletonColumns };
