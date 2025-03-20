'use client';

import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  RowData,
  Table as ReactTable,
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
import { ServerOff } from 'lucide-react';

interface DataTableProps<TData, TValue> {
  columns: ColumnDef<TData, TValue>[];
  data: TData[];
  isError?: boolean;
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
  isError,
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
            <TableBodyContent table={table} isError={isError} />
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

function TableBodyContent<TData extends RowData>({
  table,
  isError,
}: {
  table: ReactTable<TData>;
  isError?: boolean;
}) {
  if (isError) {
    return (
      <TableRow>
        <TableCell colSpan={table._getColumnDefs().length} className="h-24">
          <span className="flex justify-center gap-2.5">
            <ServerOff />
            Could not fetch products.
          </span>
        </TableCell>
      </TableRow>
    );
  }

  if (table.getRowModel().rows?.length === 0) {
    return (
      <TableRow>
        <TableCell
          colSpan={table._getColumnDefs().length}
          className="h-24 text-center"
        >
          No products yet.
        </TableCell>
      </TableRow>
    );
  }

  return table.getRowModel().rows.map((row) => (
    <TableRow key={row.id} data-state={row.getIsSelected() && 'selected'}>
      {row.getVisibleCells().map((cell) => (
        <TableCell key={cell.id}>
          {flexRender(cell.column.columnDef.cell, cell.getContext())}
        </TableCell>
      ))}
    </TableRow>
  ));
}
