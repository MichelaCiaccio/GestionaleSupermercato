'use client';

import { useEffect } from 'react';
import { toast } from 'sonner';

export function ShowToast({
  message,
  isError,
}: {
  message: string;
  isError: boolean;
}) {
  useEffect(() => {
    if (!isError) return;
    toast.warning(message);
  }, [message, isError]);

  return null;
}
