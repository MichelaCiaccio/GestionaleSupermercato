'use client';

import { ColumnDef } from '@tanstack/react-table';
import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { MoreHorizontal } from 'lucide-react';
import {
  AscDescTableHead,
  SelectAllCheckbox,
  SelectCheckbox,
} from '@/components/ui/data-table';
import { Product } from '@/types/db';
import { currencyFormatter } from '@/lib/utils';

export const columns: ColumnDef<Product>[] = [
  {
    id: 'select',
    header: ({ table }) => <SelectAllCheckbox table={table} />,
    cell: ({ row }) => <SelectCheckbox row={row} />,
    enableSorting: false,
    enableHiding: false,
  },
  {
    accessorKey: 'name',
    header: ({ column }) => {
      return (
        <AscDescTableHead title="Name" column={column} className="-ml-3" />
      );
    },
    cell: ({ row }) => <div className="capitalize">{row.getValue('name')}</div>,
  },
  {
    accessorKey: 'category',
    header: ({ column }) => {
      return (
        <AscDescTableHead title="Category" column={column} className="-ml-3" />
      );
    },
    cell: ({ row }) => row.original.category.name,
  },
  {
    accessorKey: 'sellingPrice',
    header: ({ column }) => {
      return (
        <div className="text-right">
          <AscDescTableHead title="Price" column={column} className="-mr-3" />
        </div>
      );
    },
    cell: ({ row }) => (
      <div className="text-right">
        {currencyFormatter.format(row.original.sellingPrice / 100)}
      </div>
    ),
  },
  {
    id: 'actions',
    enableHiding: false,
    cell: ({ row }) => {
      const supplier = row.original;

      return (
        <div className="text-right">
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" className="h-8 w-8 p-0">
                <span className="sr-only">Open menu</span>
                <MoreHorizontal />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuLabel>Actions</DropdownMenuLabel>
              <DropdownMenuItem
                onClick={() =>
                  navigator.clipboard.writeText(supplier.id.toString())
                }
              >
                Copy product ID
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              <DropdownMenuItem>Edit</DropdownMenuItem>
              <DropdownMenuItem variant="destructive">Delete</DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      );
    },
  },
];
