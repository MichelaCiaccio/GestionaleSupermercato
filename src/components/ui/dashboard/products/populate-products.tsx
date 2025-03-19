'use client';

import { CreateProductDTO } from '@/types/db';
import { Button } from '../../button';
import axios from 'axios';
import { env } from '@/data/env/client';

const products: CreateProductDTO[] = [
  { name: 'Apple', sellingPrice: 0.5, category: { name: 'Fruits' } },
  { name: 'Banana', sellingPrice: 0.3, category: { name: 'Fruits' } },
  { name: 'Orange', sellingPrice: 0.6, category: { name: 'Fruits' } },
  { name: 'Grapes', sellingPrice: 1.2, category: { name: 'Fruits' } },
  { name: 'Strawberry', sellingPrice: 2.5, category: { name: 'Fruits' } },
  { name: 'Carrot', sellingPrice: 0.4, category: { name: 'Vegetables' } },
  { name: 'Broccoli', sellingPrice: 0.8, category: { name: 'Vegetables' } },
  { name: 'Tomato', sellingPrice: 0.5, category: { name: 'Vegetables' } },
  { name: 'Cucumber', sellingPrice: 0.7, category: { name: 'Vegetables' } },
  { name: 'Lettuce', sellingPrice: 1.0, category: { name: 'Vegetables' } },
  { name: 'Milk', sellingPrice: 1.5, category: { name: 'Dairy' } },
  { name: 'Cheese', sellingPrice: 2.0, category: { name: 'Dairy' } },
  { name: 'Yogurt', sellingPrice: 1.0, category: { name: 'Dairy' } },
  { name: 'Butter', sellingPrice: 1.8, category: { name: 'Dairy' } },
  { name: 'Eggs', sellingPrice: 2.5, category: { name: 'Dairy' } },
  { name: 'Bread', sellingPrice: 1.2, category: { name: 'Bakery' } },
  { name: 'Bagel', sellingPrice: 1.0, category: { name: 'Bakery' } },
  { name: 'Croissant', sellingPrice: 1.5, category: { name: 'Bakery' } },
  { name: 'Muffin', sellingPrice: 1.8, category: { name: 'Bakery' } },
  { name: 'Donut', sellingPrice: 1.0, category: { name: 'Bakery' } },
  { name: 'Chicken Breast', sellingPrice: 3.5, category: { name: 'Meat' } },
  { name: 'Ground Beef', sellingPrice: 4.0, category: { name: 'Meat' } },
  { name: 'Pork Chop', sellingPrice: 3.8, category: { name: 'Meat' } },
  { name: 'Salmon Fillet', sellingPrice: 5.5, category: { name: 'Meat' } },
  { name: 'Bacon', sellingPrice: 2.5, category: { name: 'Meat' } },
  { name: 'Soda', sellingPrice: 1.0, category: { name: 'Beverages' } },
  { name: 'Juice', sellingPrice: 1.5, category: { name: 'Beverages' } },
  { name: 'Water', sellingPrice: 0.5, category: { name: 'Beverages' } },
  { name: 'Coffee', sellingPrice: 2.0, category: { name: 'Beverages' } },
  { name: 'Tea', sellingPrice: 1.8, category: { name: 'Beverages' } },
  { name: 'Chips', sellingPrice: 1.2, category: { name: 'Snacks' } },
  { name: 'Cookies', sellingPrice: 1.5, category: { name: 'Snacks' } },
  { name: 'Candy Bar', sellingPrice: 1.0, category: { name: 'Snacks' } },
  { name: 'Popcorn', sellingPrice: 1.8, category: { name: 'Snacks' } },
  { name: 'Nuts', sellingPrice: 2.0, category: { name: 'Snacks' } },
  { name: 'Pineapple', sellingPrice: 1.5, category: { name: 'Fruits' } },
  { name: 'Kiwi', sellingPrice: 0.8, category: { name: 'Fruits' } },
  { name: 'Blueberry', sellingPrice: 2.0, category: { name: 'Fruits' } },
  { name: 'Peach', sellingPrice: 1.0, category: { name: 'Fruits' } },
  { name: 'Plum', sellingPrice: 0.9, category: { name: 'Fruits' } },
  { name: 'Zucchini', sellingPrice: 0.6, category: { name: 'Vegetables' } },
  { name: 'Spinach', sellingPrice: 1.2, category: { name: 'Vegetables' } },
  { name: 'Bell Pepper', sellingPrice: 0.8, category: { name: 'Vegetables' } },
  { name: 'Onion', sellingPrice: 0.5, category: { name: 'Vegetables' } },
  { name: 'Garlic', sellingPrice: 0.3, category: { name: 'Vegetables' } },
  { name: 'Cheddar Cheese', sellingPrice: 2.5, category: { name: 'Dairy' } },
  { name: 'Sour Cream', sellingPrice: 1.2, category: { name: 'Dairy' } },
  { name: 'Cottage Cheese', sellingPrice: 1.5, category: { name: 'Dairy' } },
  { name: 'Whipping Cream', sellingPrice: 1.8, category: { name: 'Dairy' } },
];

async function createProduct(product: CreateProductDTO) {
  await axios.post(
    new URL('/products/add', env.NEXT_PUBLIC_DB_BASE_URL).toString(),
    {
      ...product,
      stocks: [],
    }
  );
  console.log(`Added: ${product.name}`);
}

export function PopulateProductsButton(props: React.ComponentProps<'button'>) {
  const populate = async () => {
    console.log('Populating products...');
    for (const product of products) {
      product.sellingPrice *= 100;
      await createProduct(product);
    }
    console.log('Done products population!');
  };

  return (
    <Button className="cursor-pointer" onClick={populate} {...props}>
      Generate sample products
    </Button>
  );
}
