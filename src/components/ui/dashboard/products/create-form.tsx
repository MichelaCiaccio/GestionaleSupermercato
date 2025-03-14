'use client';

import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
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
import db from '@/lib/db';

const schema = z.object({
  name: z.string().min(1, { message: 'Name is required.' }),
  sellingPrice: z.coerce
    .number()
    .gt(0, { message: 'Please enter an amount greater than €0.' }),
  category: z.string().min(1, { message: 'Category is required.' }),
});

export function CreateProductForm() {
  const form = useForm<z.infer<typeof schema>>({
    resolver: zodResolver(schema),
    defaultValues: {
      name: '',
      sellingPrice: 0,
      category: '',
    },
  });

  const onSubmit = ({
    name,
    sellingPrice,
    category,
  }: z.infer<typeof schema>) => {
    db.products
      .create({ name, sellingPrice, category: { name: category } })
      .then(() => form.reset());
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        <FormField
          control={form.control}
          name="name"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Product Name</FormLabel>
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
          Submit
        </Button>
      </form>
    </Form>
  );
}
