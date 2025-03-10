'use client';

import { toast } from 'sonner';

import { Button } from '@/components/ui/button';
import { Mail } from 'lucide-react';

export function ChangePasswordButton() {
  return (
    <span>
      <Button
        type="button"
        variant="link"
        className="m-0 inline-block h-auto cursor-pointer p-0 text-sm font-normal"
        onClick={() =>
          toast('Check your email', {
            icon: <Mail />,
            description: 'An email has been sent to reset your password.',
          })
        }
      >
        Forgot password?
      </Button>
    </span>
  );
}
