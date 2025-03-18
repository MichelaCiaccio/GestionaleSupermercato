'use client';

import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  useReactTable,
} from '@tanstack/react-table';
import { useEffect } from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { DataTableFooter } from '../../data-table';

interface DataTableProps<TData, TValue> {
  columns: ColumnDef<TData, TValue>[];
  data: TData[];
  // WARNING: Data filtering, sorting, and processing must be done by the backend.
  // For now, this will be handled in the client with the following
  // helper props:
  search: string;
  currentPage: number;
  totalPages: number;
  order: 'asc' | 'desc';
  sort: string;
}

export function ProductsDataTable<TData, TValue>({
  columns,
  data,
  search,
  currentPage,
  totalPages,
  order,
  sort,
}: DataTableProps<TData, TValue>) {
  const table = useReactTable({
    data,
    columns,
    getCoreRowModel: getCoreRowModel(),
    // WARNING: This is paginating in the client, use backend pagination in the future.
    getPaginationRowModel: getPaginationRowModel(),
    // WARNING: This is sorting in the client, use backend sorting in the future.
    getSortedRowModel: getSortedRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    initialState: {
      pagination: { pageIndex: currentPage - 1, pageSize: 20 },
    },
  });

  useEffect(() => {
    table.setPagination({ pageIndex: currentPage - 1, pageSize: 20 });
  }, [table, currentPage]);

  useEffect(() => {
    table.getColumn('name')?.setFilterValue(search);
  }, [table, search]);

  useEffect(() => {
    table.getColumn(sort)?.toggleSorting(order === 'desc');
  }, [table, order, sort]);

  return (
    <>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => {
                  return (
                    <TableHead key={header.id}>
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

      <DataTableFooter
        table={table}
        currentPage={currentPage}
        totalPages={totalPages}
      />
    </>
  );
}
