'use client';

import { useEffect, useState } from 'react';
import GridLoader from 'react-spinners/GridLoader';

function Spinner() {
  return (
    <GridLoader
      size={30}
      color="var(--primary)"
      aria-label="Loading Spinner"
      data-testid="loader"
    />
  );
}

function ScreenSpinner({ label }: { label: string }) {
  return (
    <div className="fixed top-0 left-0 z-50 flex h-full w-full flex-col items-center justify-center gap-6 bg-black/40">
      <Spinner />

      <p className="text-xl font-semibold dark:font-normal">
        <DotsLabel label={label} />
      </p>
    </div>
  );
}

function DotsLabel({ label: defaultLabel }: { label: string }) {
  const [label, setLabel] = useState(defaultLabel);

  useEffect(() => {
    let dots = '';

    const interval = setInterval(() => {
      dots = dots.length < 3 ? dots + '.' : '';
      setLabel(`${defaultLabel}${dots}`);
    }, 300);

    return () => clearInterval(interval);
  }, [defaultLabel]);

  return label;
}

export { Spinner, ScreenSpinner };
