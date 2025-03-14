import * as React from 'react';

import { cn } from '@/lib/utils';

function Input({
  className,
  type,
  icon,
  coloredError,
  ...props
}: React.ComponentProps<'input'> & {
  icon?: React.ReactNode;
  coloredError?: boolean;
}) {
  const isError = props['aria-invalid'] && coloredError;
  const ariaInvalidClasses = isError
    ? 'ring-destructive/20 dark:ring-destructive/40 border-destructive'
    : 'focus-within:border-ring';

  return (
    <div
      className={cn(
        'flex',
        'rounded-md border bg-transparent shadow-xs transition-[color,box-shadow]',
        'focus-within:ring-ring/50 rounded-md focus-within:ring-[3px]',
        ariaInvalidClasses,
        className
      )}
    >
      {!!icon && (
        <span
          className={cn(
            'ml-3 flex items-center',
            isError ? 'text-destructive-foreground' : 'text-muted-foreground'
          )}
        >
          {icon}
        </span>
      )}
      <input
        type={type}
        data-slot="input"
        className={cn(
          'border-input file:text-foreground placeholder:text-muted-foreground selection:bg-primary selection:text-primary-foreground flex h-9 w-full min-w-0 px-3 py-1 text-base outline-none file:inline-flex file:h-7 file:border-0 file:bg-transparent file:text-sm file:font-medium disabled:pointer-events-none disabled:cursor-not-allowed disabled:opacity-50 md:text-sm'
        )}
        {...props}
      />
    </div>
  );
}

export { Input };
