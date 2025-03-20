'use client';

import { Button } from '@/components/ui/button';
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from '@/components/ui/alert-dialog';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { MoreHorizontal } from 'lucide-react';
import { ColumnsBuilder, SortTableHead } from '@/components/ui/data-table';
import { Product } from '@/types/db';
import { currencyFormatter } from '@/lib/utils';
import { useActionState } from 'react';
import { deleteProduct } from './actions';
import Link from 'next/link';
import { Skeleton } from '../../skeleton';

export type ProductSkeleton = {
  name: number;
  category: number;
  sellingPrice: number;
};

const [columns, skeletonColumns] = new ColumnsBuilder<
  Product,
  ProductSkeleton
>()
  .addColumn(
    {
      accessorKey: 'name',
      header: () => <SortTableHead title="Name" value="name" />,
      cell: ({ row }) => (
        <div className="ml-3 capitalize">{row.getValue('name')}</div>
      ),
    },
    {
      accessorKey: 'name',
      header: () => <SortTableHead title="Name" value="name" />,
      cell: ({ row }) => (
        <Skeleton className={`ml-3 h-6`} style={{ width: row.original.name }} />
      ),
    }
  )
  .addColumn(
    {
      accessorKey: 'category',
      header: () => (
        <SortTableHead title="Category" value="category" className="-ml-3" />
      ),
      cell: ({ row }) => row.original.category.name,
      sortingFn: (rowA, rowB) => {
        return rowA.original.category.name.localeCompare(
          rowB.original.category.name
        );
      },
    },
    {
      accessorKey: 'category',
      header: () => (
        <SortTableHead title="Category" value="category" className="-ml-3" />
      ),
      cell: ({ row }) => (
        <Skeleton className={`h-6`} style={{ width: row.original.category }} />
      ),
    }
  )
  .addColumn(
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
        <div className="text-right">
          {currencyFormatter.format(row.original.sellingPrice / 100)}
        </div>
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
    }
  )
  .addColumn(
    {
      id: 'actions',
      enableHiding: false,
      cell: ({ row }) => {
        const product = row.original;

        return (
          <div className="text-right">
            <ProductDropdownMenu product={product} />
          </div>
        );
      },
    },
    {
      id: 'actions',
      enableHiding: false,
      cell: () => {
        return <Skeleton className="float-right h-8 w-8" />;
      },
    }
  )
  .build();

export { columns, skeletonColumns };

function ProductDropdownMenu({ product }: { product: Product }) {
  const deleteProductWithId = deleteProduct.bind(null, product.id);
  const formAction = useActionState(deleteProductWithId, {
    message: '',
    success: false,
  })[1];

  return (
    <AlertDialog>
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
            onClick={() => navigator.clipboard.writeText(product.id.toString())}
          >
            Copy product ID
          </DropdownMenuItem>
          <DropdownMenuSeparator />
          <DropdownMenuItem asChild>
            <Link href={`/dashboard/products/${product.id}/edit`}>Edit</Link>
          </DropdownMenuItem>

          <AlertDialogTrigger asChild>
            <DropdownMenuItem variant="destructive">Delete</DropdownMenuItem>
          </AlertDialogTrigger>
        </DropdownMenuContent>
      </DropdownMenu>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>
            Are you sure to delete this product?
          </AlertDialogTitle>
          <AlertDialogDescription>
            This action cannot be undone. This will permanently delete the
            product {product.name}. Do you wish to continue?
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel className="cursor-pointer">No</AlertDialogCancel>
          <AlertDialogAction
            variant="destructive"
            className="cursor-pointer"
            asChild
          >
            <form action={formAction}>
              <button className="cursor-pointer">Yes, Delete</button>
            </form>
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
