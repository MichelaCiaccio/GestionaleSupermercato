import { Product, CreateProductDTO, UpdateProductDTO } from '@/types/db';
import instance from './instance';
import { AxiosResponse } from 'axios';

export const getAll = async (): Promise<Product[]> => {
  const response = await instance.get<Product[]>(`/product/all`);
  return response.data;
};

export const getById = async (id: number): Promise<Product> => {
  const response = await instance.get<Product>(`/product/${id}`);
  return response.data;
};

export const getByName = async (name: string): Promise<Product> => {
  const response = await instance.get<Product>(`/product/name/${name}`);
  return response.data;
};

export const create = async (data: CreateProductDTO): Promise<string> => {
  const response = await instance.post<
    string,
    AxiosResponse,
    CreateProductDTO & Pick<Product, 'stocks'>
  >('/product/add', { ...data, stocks: [] });
  return response.data;
};

export const update = async (
  id: number,
  data: UpdateProductDTO
): Promise<Product> => {
  const response = await instance.put<Product>(`/product/${id}`, data);
  return response.data;
};

export const deleteById = async (id: number): Promise<string> => {
  const response = await instance.delete<string>(`/product/id/?id=${id}`);
  return response.data;
};
