'use client';

import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Euro, Package, Tag } from 'lucide-react';
import { useActionState } from 'react';
import { ProductFormState, updateProduct } from './actions';
import { ProductSchema, ProductValues } from '@/lib/entities/product';
import { toast } from 'sonner';
import { Product } from '@/types/db';
import { redirect } from 'next/navigation';

const resolver = zodResolver(ProductSchema);

export function EditProductForm(props: { product: Product }) {
  const product = props.product;
  const initialState: ProductFormState = {
    message: '',
    success: false,
    values: {
      name: product.name,
      sellingPrice: product.sellingPrice / 100,
      category: product.category.name,
    },
  };
  const [state, formAction] = useActionState(
    async (prevState: ProductFormState, formData: FormData) => {
      const result = await updateProduct(product.id, prevState, formData);
      if (result.success) {
        toast('Product updated', {
          icon: <Package />,
          description: result.message,
        });
        redirect('/dashboard/products');
      } else {
        toast.error(result.message);
      }
      return prevState;
    },
    initialState
  );

  const form = useForm<ProductValues>({
    resolver,
    errors: state.errors,
    values: state.values,
  });

  return (
    <Form {...form}>
      <form action={formAction} className="space-y-8">
        <FormField
          control={form.control}
          name="name"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Name</FormLabel>
              <FormControl>
                <Input
                  placeholder="e.g., T-shirt"
                  icon={<Package className="h-5 w-5" strokeWidth={1} />}
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="sellingPrice"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Selling Price</FormLabel>
              <FormControl>
                <Input
                  type="number"
                  step="any"
                  placeholder="e.g., 29.99"
                  icon={<Euro className="h-5 w-5" strokeWidth={1} />}
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="category"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Category</FormLabel>
              <FormControl>
                <Input
                  placeholder="e.g., Clothing"
                  icon={<Tag className="h-5 w-5" strokeWidth={1} />}
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button
          type="submit"
          className="ml-auto block w-full cursor-pointer px-10 md:w-auto"
        >
          Edit Product
        </Button>
      </form>
    </Form>
  );
}
