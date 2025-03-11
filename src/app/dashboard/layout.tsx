import { AppSidebar } from '@/components/ui/dashboard/app-sidebar';
import Header from '@/components/ui/dashboard/header';
import { SidebarProvider } from '@/components/ui/sidebar';
import { Metadata } from 'next';
import { ReactNode } from 'react';

export const metadata: Metadata = {
  title: 'Dashboard',
};

export default function DashboardLayout({ children }: { children: ReactNode }) {
  return (
    <SidebarProvider>
      <AppSidebar
        user={{
          name: 'Sherwin Arellano',
          avatar: '/',
          avatarFallback: 'SA',
          email: 's.arellano@esis-italia.com',
        }}
      />

      <main className="flex-1">
        <Header title="Dashboard" />
        {children}
      </main>
    </SidebarProvider>
  );
}
