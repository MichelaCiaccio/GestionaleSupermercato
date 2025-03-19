'use client';

import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '../../table';
import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  useReactTable,
} from '@tanstack/react-table';
import { DataTableFooter, SortTableHead } from '../../data-table';
import { Skeleton } from '../../skeleton';
import { useState } from 'react';

type ProductSkeleton = {
  name: number;
  category: number;
  sellingPrice: number;
};

const columns: ColumnDef<ProductSkeleton>[] = [
  {
    accessorKey: 'name',
    header: () => <SortTableHead title="Name" value="name" />,
    cell: ({ row }) => (
      <Skeleton className={`ml-3 h-6`} style={{ width: row.original.name }} />
    ),
  },
  {
    accessorKey: 'category',
    header: () => (
      <SortTableHead title="Category" value="category" className="-ml-3" />
    ),
    cell: ({ row }) => (
      <Skeleton className={`h-6`} style={{ width: row.original.category }} />
    ),
  },
  {
    accessorKey: 'sellingPrice',
    header: () => (
      <SortTableHead
        title="Price"
        value="sellingPrice"
        className="float-right -mr-3"
      />
    ),
    cell: ({ row }) => (
      <Skeleton
        className={`float-right h-6`}
        style={{ width: row.original.sellingPrice }}
      />
    ),
  },
  {
    id: 'actions',
    enableHiding: false,
    cell: () => {
      return <Skeleton className="float-right h-8 w-8" />;
    },
  },
];

export function ProductsTableSkeleton() {
  const [data] = useState<ProductSkeleton[]>(() =>
    Array.from(Array(20)).map(() => ({
      name: Math.floor(Math.random() * 110 + 50),
      category: Math.floor(Math.random() * 110 + 50),
      sellingPrice: Math.floor(Math.random() * 50 + 30),
    }))
  );
  const table = useReactTable({
    data,
    columns,
    getCoreRowModel: getCoreRowModel(),
  });

  return (
    <>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => {
                  return (
                    <TableHead
                      key={header.id}
                      className={header.id === 'actions' ? 'w-8' : ''}
                    >
                      {header.isPlaceholder
                        ? null
                        : flexRender(
                            header.column.columnDef.header,
                            header.getContext()
                          )}
                    </TableHead>
                  );
                })}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {table.getRowModel().rows?.length ? (
              table.getRowModel().rows.map((row) => (
                <TableRow
                  key={row.id}
                  data-state={row.getIsSelected() && 'selected'}
                >
                  {row.getVisibleCells().map((cell) => (
                    <TableCell key={cell.id}>
                      {flexRender(
                        cell.column.columnDef.cell,
                        cell.getContext()
                      )}
                    </TableCell>
                  ))}
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell
                  colSpan={columns.length}
                  className="h-24 text-center"
                >
                  No products yet.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>

      <DataTableFooter currentPage={1} totalPages={1} />
    </>
  );
}
