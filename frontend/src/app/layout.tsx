import type { Metadata } from "next";
import { Inter } from "next/font/google";
import Link from "next/link";
// @ts-ignore - allow side-effect css import without type declarations
import "./globals.css";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "AlexAuto | Premium Car Marketplace",
  description: "A high-end car selling platform",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <head>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet"></link>
      </head>
      <body
        className={`${inter.className} antialiased`}
      >
        {/* Navigation Header */}
        <nav className="sticky top-0 z-50 bg-white/80 backdrop-blur-md border-b border-gray-100">
          <div className="max-w-[1600px] mx-auto px-4 md:px-8 h-16 flex items-center justify-between">
            <div className="flex items-center gap-12">
              <Link 
                href="/" 
                className="text-2xl font-[900] tracking-tighter text-gray-900 flex items-center gap-2"
              >
                AlexAuto
              </Link>
              <div className="hidden md:flex items-center gap-8">
                <Link 
                  href="/" 
                  className={`text-[10px] font-black uppercase tracking-[0.2em] transition-colors 'text-blue-600' : 'text-gray-400 hover:text-gray-900'`}
                >
                  Inventory
                </Link>
                <Link 
                  href="/sell" 
                  className={`text-[10px] font-black uppercase tracking-[0.2em] transition-colors 'text-blue-600' : 'text-gray-400 hover:text-gray-900'`}
                >
                  Sell My Car
                </Link>
              </div>
            </div>
          </div>
        </nav>

        <main>{children}</main>

        <footer className="bg-white border-t border-gray-100 py-12">
        <div className="max-w-[1600px] mx-auto px-8 flex flex-col md:flex-row justify-between items-center gap-8">
          <div className="text-center md:text-left">
            <span className="text-xl font-black text-gray-900">AlexAuto</span>
            <p className="text-gray-400 text-sm mt-1">© 2025 AlexAuto Premium Marketplace. All rights reserved.</p>
          </div>
          <div className="flex gap-8">
            <Link href="#" className="text-gray-400 hover:text-gray-900 transition-colors">Privacy</Link>
            <Link href="#" className="text-gray-400 hover:text-gray-900 transition-colors">Terms</Link>
            <Link href="#" className="text-gray-400 hover:text-gray-900 transition-colors">Cookies</Link>
          </div>
        </div>
        </footer>

      </body>
    </html>
  );
}
