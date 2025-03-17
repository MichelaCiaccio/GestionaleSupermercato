import { Checkbox } from '@/components/ui/checkbox';
import { Button, buttonVariants } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { Input } from '@/components/ui/input';
import { Column, Row, RowData, Table } from '@tanstack/react-table';
import {
  ArrowUpDown,
  ChevronLeft,
  ChevronRight,
  ChevronsLeft,
  ChevronsRight,
  Settings2,
} from 'lucide-react';
import { ReactNode } from 'react';
import * as CheckboxPrimitive from '@radix-ui/react-checkbox';
import { cn } from '@/lib/utils';
import { VariantProps } from 'class-variance-authority';

export function InputFilter<TData extends RowData>({
  table,
  className,
  ...props
}: React.ComponentProps<'input'> & {
  table: Table<TData>;
}) {
  return (
    <Input
      value={(table.getColumn('name')?.getFilterValue() as string) ?? ''}
      onChange={(event) =>
        table.getColumn('name')?.setFilterValue(event.target.value)
      }
      className={className}
      {...props}
    />
  );
}

export function ViewColumnsFilterDropdown<TData extends RowData>({
  table,
  className,
  ...props
}: React.ComponentProps<'button'> &
  VariantProps<typeof buttonVariants> & {
    asChild?: boolean;
  } & {
    table: Table<TData>;
  }) {
  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button
          variant="outline"
          className={cn('cursor-pointer', className)}
          {...props}
        >
          <Settings2 />
          View
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end">
        {table
          .getAllColumns()
          .filter((column) => column.getCanHide())
          .map((column) => {
            return (
              <DropdownMenuCheckboxItem
                key={column.id}
                className="capitalize"
                checked={column.getIsVisible()}
                onCheckedChange={(value) => column.toggleVisibility(!!value)}
              >
                {column.id}
              </DropdownMenuCheckboxItem>
            );
          })}
      </DropdownMenuContent>
    </DropdownMenu>
  );
}

export function DataTableFooter<TData extends RowData>({
  table,
}: {
  table: Table<TData>;
}) {
  return (
    <div className="flex items-center justify-end space-x-2 py-4">
      <div className="text-muted-foreground flex-1 text-sm">
        {table.getFilteredSelectedRowModel().rows.length} of{' '}
        {table.getFilteredRowModel().rows.length} row(s) selected.
      </div>

      <div className="flex gap-2.5">
        <div className="space-x-2">
          <Button
            variant="outline"
            className="h-8 w-8 cursor-pointer p-0"
            onClick={() => table.setPageIndex(0)}
            disabled={!table.getCanPreviousPage()}
          >
            <span className="sr-only">Go to first page</span>
            <ChevronsLeft />
          </Button>
          <Button
            variant="outline"
            className="h-8 w-8 cursor-pointer p-0"
            onClick={() => table.previousPage()}
            disabled={!table.getCanPreviousPage()}
          >
            <span className="sr-only">Go to previous page</span>
            <ChevronLeft />
          </Button>
        </div>

        <div className="flex items-center text-sm font-medium">
          Page {table.getState().pagination.pageIndex + 1} of{' '}
          {table.getPageCount()}
        </div>

        <div className="space-x-2">
          <Button
            variant="outline"
            className="h-8 w-8 cursor-pointer p-0"
            onClick={() => table.nextPage()}
            disabled={!table.getCanNextPage()}
          >
            <span className="sr-only">Go to next page</span>
            <ChevronRight />
          </Button>
          <Button
            variant="outline"
            className="h-8 w-8 cursor-pointer p-0"
            onClick={() => table.setPageIndex(table.getPageCount() - 1)}
            disabled={!table.getCanNextPage()}
          >
            <span className="sr-only">Go to last page</span>
            <ChevronsRight />
          </Button>
        </div>
      </div>
    </div>
  );
}

export function SelectAllCheckbox<TData extends RowData>({
  table,
  className,
  ...props
}: React.ComponentProps<typeof CheckboxPrimitive.Root> & {
  table: Table<TData>;
}) {
  return (
    <Checkbox
      className={cn('cursor-pointer', className)}
      checked={
        table.getIsAllPageRowsSelected() ||
        (table.getIsSomePageRowsSelected() && 'indeterminate')
      }
      onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
      aria-label="Select all"
      {...props}
    />
  );
}

export function SelectCheckbox<TData extends RowData>({
  row,
  className,
  ...props
}: React.ComponentProps<typeof CheckboxPrimitive.Root> & {
  row: Row<TData>;
}) {
  return (
    <Checkbox
      className={cn('cursor-pointer', className)}
      checked={row.getIsSelected()}
      onCheckedChange={(value) => row.toggleSelected(!!value)}
      aria-label="Select row"
      {...props}
    />
  );
}

export function AscDescTableHead<TData extends RowData>({
  title,
  column,
  className,
  ...props
}: React.ComponentProps<'button'> &
  VariantProps<typeof buttonVariants> & {
    asChild?: boolean;
  } & {
    title: ReactNode;
    column: Column<TData>;
  }) {
  return (
    <Button
      type="button"
      className={cn('cursor-pointer', className)}
      variant="ghost"
      onClick={() => column.toggleSorting(column.getIsSorted() === 'asc')}
      {...props}
    >
      {title}
      <ArrowUpDown />
    </Button>
  );
}
