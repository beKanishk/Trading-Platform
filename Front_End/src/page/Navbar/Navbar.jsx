import React from "react";
import {
  Sheet,
  SheetTrigger,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetDescription
} from "@/components/ui/sheet";
import { Button } from "@/components/ui/button";
import { Menu, Search } from "lucide-react";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import Sidebar from "../Sidebar/Sidebar";
import { AvatarFallback } from "@radix-ui/react-avatar";
import { useSelector } from "react-redux";

const Navbar = () => {
    const auth = useSelector((state) => state.auth);
  return (
    <div className="px-4 py-3 border-b z-50 bg-background sticky top-0 flex justify-between items-center">
        <div className="flex items-center gap-3">
            <Sheet>
            <SheetTrigger asChild>
                <Button
                variant="ghost"
                size="icon"
                className="rounded-full h-11 w-11"
                >
                <Menu className="h-7 w-7" />
                </Button>
            </SheetTrigger>
            <SheetContent side="left" className="w-72 border-r-0 flex flex-col justify-center">
                <SheetHeader>
                <SheetTitle>
                    <div className="text-3xl flex justify-center items-center gap-1">
                        <Avatar>
                            <AvatarImage src="https://github.com/shadcn.png" alt="avatar image"/>
                        </Avatar>
                        <div>
                            <span className="font-bold text-orange-400">Being</span>
                        </div>
                    </div>
                </SheetTitle>
                
                </SheetHeader>
                <Sidebar/>
            </SheetContent>
            </Sheet>
            <p className="text-sm lg:text-base cursor-pointer">
                Crypto Trading
            </p>
            <div className="p-0 ml-9">
                <Button className="flex items-center gap-3">
                    
                    <Search/>
                    <span>Search</span>
                </Button>
            </div>
        </div>
        <div>
            <Avatar>
                <AvatarFallback>
                    {auth?.user?.name ? auth.user.name[0].toUpperCase() : ""}
                </AvatarFallback>
            </Avatar>
        </div>
        
    </div>

  );
};

export default Navbar;
