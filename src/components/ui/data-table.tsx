'use client';

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
import { usePathname, useRouter, useSearchParams } from 'next/navigation';
import { useDebouncedCallback } from 'use-debounce';

export function SearchInput(props: React.ComponentProps<'input'>) {
  const searchParams = useSearchParams();
  const pathname = usePathname();
  const { replace } = useRouter();

  const handleSearch = useDebouncedCallback((term: string) => {
    const params = new URLSearchParams(searchParams);

    params.set('page', '1');

    if (term) {
      params.set('search', term);
    } else {
      params.delete('search');
    }

    replace(`${pathname}?${params.toString()}`);
  }, 300);

  return (
    <Input
      {...props}
      onChange={(e) => handleSearch(e.target.value)}
      defaultValue={searchParams.get('search')?.toString()}
    />
  );
}

/**
 * @deprecated In favor of SearchInput, this component is marked to be removed
 *             once the new table logic with query parameters is implemented.
 */
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
  currentPage,
  totalPages,
}: {
  table: Table<TData>;
  currentPage: number;
  totalPages: number;
}) {
  return (
    <div className="flex items-center justify-end space-x-2 py-4">
      <div className="text-muted-foreground flex-1 text-sm">
        {table.getFilteredSelectedRowModel().rows.length} of{' '}
        {table.getFilteredRowModel().rows.length} row(s) selected.
      </div>

      <Pagination currentPage={currentPage} totalPages={totalPages} />
    </div>
  );
}

export function Pagination({
  currentPage,
  totalPages,
}: {
  currentPage: number;
  totalPages: number;
}) {
  const searchParams = useSearchParams();
  const pathname = usePathname();
  const { replace } = useRouter();

  const nextPage = () => {
    const params = new URLSearchParams(searchParams);

    const next = (Number(params.get('page')) || 1) + 1;

    if (next <= totalPages) {
      params.set('page', next.toString());
      replace(`${pathname}?${params.toString()}`);
    }
  };

  const prevPage = () => {
    const params = new URLSearchParams(searchParams);

    const prev = (Number(params.get('page')) || 1) - 1;

    if (prev >= 1) {
      params.set('page', prev.toString());
      replace(`${pathname}?${params.toString()}`);
    }
  };

  const firstPage = () => {
    const params = new URLSearchParams(searchParams);
    params.set('page', '1');
    replace(`${pathname}?${params.toString()}`);
  };

  const lastPage = () => {
    const params = new URLSearchParams(searchParams);
    params.set('page', totalPages.toString());
    replace(`${pathname}?${params.toString()}`);
  };

  return (
    <div className="flex gap-2.5">
      <div className="space-x-2">
        <Button
          variant="outline"
          className="h-8 w-8 cursor-pointer p-0"
          onClick={firstPage}
          disabled={currentPage <= 1}
        >
          <span className="sr-only">Go to first page</span>
          <ChevronsLeft />
        </Button>
        <Button
          variant="outline"
          className="h-8 w-8 cursor-pointer p-0"
          onClick={prevPage}
          disabled={currentPage <= 1}
        >
          <span className="sr-only">Go to previous page</span>
          <ChevronLeft />
        </Button>
      </div>

      <div className="flex items-center text-sm font-medium">
        Page {currentPage} of {totalPages}
      </div>

      <div className="space-x-2">
        <Button
          variant="outline"
          className="h-8 w-8 cursor-pointer p-0"
          onClick={nextPage}
          disabled={currentPage >= totalPages}
        >
          <span className="sr-only">Go to next page</span>
          <ChevronRight />
        </Button>
        <Button
          variant="outline"
          className="h-8 w-8 cursor-pointer p-0"
          onClick={lastPage}
          disabled={currentPage >= totalPages}
        >
          <span className="sr-only">Go to last page</span>
          <ChevronsRight />
        </Button>
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
