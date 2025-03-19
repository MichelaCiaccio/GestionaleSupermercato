import { Product, CreateProductDTO, UpdateProductDTO } from '@/types/db';
import instance from './instance';
import { AxiosResponse } from 'axios';

const route = `products`;

export const get = async ({
  page,
}: {
  page?: number;
} = {}): Promise<{
  content: Product[];
  last: boolean;
  first: boolean;
  totalElements: number;
  totalPages: number;
  size: number; // max items in a page
  number: number; // current page
  numberOfElements: number; // returned items
}> => {
  const params = new URLSearchParams();

  params.set('page', page?.toString() || '0');

  const response = await instance.get(`/${route}/?${params.toString()}`);

  return response.data;
};

export const getById = async (id: number): Promise<Product> => {
  const response = await instance.get<Product>(`/${route}/id/?id=${id}`);
  return response.data;
};

export const getByName = async (name: string): Promise<Product> => {
  const response = await instance.get<Product>(`/${route}/name/${name}`);
  return response.data;
};

export const create = async (data: CreateProductDTO): Promise<string> => {
  const response = await instance.post<
    string,
    AxiosResponse,
    CreateProductDTO & Pick<Product, 'stocks'>
  >(`/${route}/add`, { ...data, stocks: [] });
  return response.data;
};

export const update = async (
  id: number,
  data: UpdateProductDTO
): Promise<string> => {
  const response = await instance.put<string, AxiosResponse, UpdateProductDTO>(
    `/${route}/id/?id=${id}`,
    data
  );
  return response.data;
};

export const deleteById = async (id: number): Promise<string> => {
  const response = await instance.delete<string>(`/${route}/id/?id=${id}`);
  return response.data;
};
