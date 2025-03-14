export type RawFormData<T extends Record<string, unknown>> = {
  [P in keyof T]: string | null;
};
