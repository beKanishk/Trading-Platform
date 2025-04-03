import React from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { useNavigate } from "react-router-dom";
import { ScrollArea } from "@/components/ui/scroll-area";

const AssetTable = ({ coin, category }) => { // ✅ Destructuring props
  const navigate = useNavigate();
  if(category == "trending"){
    coin = coin.coins
  }
  console.log("coin: ", coin);
  
  return (
    
    <Table className="w-full border-collapse">
      <ScrollArea className={`${category == "all"?"h-[77.3vh]":"h-[82vh]"}`}>
        <TableHeader>
          <TableRow>
            <TableHead className="w-[150px] text-center">Coin</TableHead>
            <TableHead className="text-center">Symbol</TableHead>
            <TableHead className="text-center">Volume</TableHead>
            <TableHead className="text-center">Market Cap</TableHead>
            <TableHead className="text-center">24h</TableHead>
            <TableHead className="text-right">Price</TableHead>
          </TableRow>
        </TableHeader>

        {(category == "trending") ? (<TableBody>
          {(coin || []).map((item, index) => ( // ✅ Ensure coin is always an array
            <TableRow key={index} className="border-b">
              <TableCell onClick={() => navigate(`/market/${item.item.id}`)} className="font-medium flex items-center gap-2 text-center">
                <Avatar className="-z-50">
                  <AvatarImage src={item.item.large || ""} alt={item.item.name || "coin"} />
                </Avatar>
                <span>{item.item.name}</span>
              </TableCell>
              <TableCell className="text-center">{item.item.symbol.toUpperCase()}</TableCell>
              <TableCell className="text-center">{item.item.data.total_volume.toLocaleString()}</TableCell>
              <TableCell className="text-center">{item.item.data.market_cap.toLocaleString()}</TableCell>
              <TableCell className={`text-center ${item.item.data.price_change_percentage_24h.usd.toLocaleString() < 0 ? "text-red-500" : "text-green-500"}`}>
                {item.item.data.price_change_percentage_24h.usd.toFixed(4)}
              </TableCell>
              <TableCell className="text-right">
                ${item.item.data.price >= 1 
                  ? item.item.data.price.toFixed(3)  // Show 2 decimal places for values >= 1
                  : parseFloat(item.item.data.price.toFixed(3))}
              </TableCell>


            </TableRow>
          ))}
        </TableBody>) : 
        (<TableBody>
          {(coin || []).map((item, index) => ( // ✅ Ensure coin is always an array
            <TableRow key={index} className="border-b">
              <TableCell onClick={() => navigate(`/market/${item.id}`)} className="font-medium flex items-center gap-2 text-center">
                <Avatar className="-z-50">
                  <AvatarImage src={item.image || ""} alt={item.name || "coin"} />
                </Avatar>
                <span>{item.name}</span>
              </TableCell>
              <TableCell className="text-center">{item.symbol.toUpperCase()}</TableCell>
              <TableCell className="text-center">{item.total_volume.toLocaleString()}</TableCell>
              <TableCell className="text-center">{item.market_cap.toLocaleString()}</TableCell>
              <TableCell className={`text-center ${item.price_change_24h.toLocaleString() < 0 ? "text-red-500" : "text-green-500"}`}>
                {item.price_change_24h.toFixed(4)}
              </TableCell>
              <TableCell className="text-right">
                ${item.current_price >= 1 
                  ? item.current_price.toFixed(3)  // Show 2 decimal places for values >= 1
                  : parseFloat(item.current_price)}
              </TableCell>


            </TableRow>
          ))}
        </TableBody>)}
        {/* <TableBody>
          {(coin || []).map((item, index) => ( // ✅ Ensure coin is always an array
            <TableRow key={index} className="border-b">
              <TableCell onClick={() => navigate(`/market/${item.id}`)} className="font-medium flex items-center gap-2 text-center">
                <Avatar className="-z-50">
                  <AvatarImage src={item.image || ""} alt={item.name || "coin"} />
                </Avatar>
                <span>{item.name}</span>
              </TableCell>
              <TableCell className="text-center">{item.symbol.toUpperCase()}</TableCell>
              <TableCell className="text-center">{item.total_volume.toLocaleString()}</TableCell>
              <TableCell className="text-center">{item.market_cap.toLocaleString()}</TableCell>
              <TableCell className={`text-center ${item.price_change_24h.toLocaleString() < 0 ? "text-red-500" : "text-green-500"}`}>
                {item.price_change_24h.toFixed(4)}
              </TableCell>
              <TableCell className="text-right">
                ${item.current_price >= 1 
                  ? item.current_price.toFixed(3)  // Show 2 decimal places for values >= 1
                  : parseFloat(item.current_price)}
              </TableCell>


            </TableRow>
          ))}
        </TableBody> */}
      </ScrollArea>
      
    </Table>
  );
};

export default AssetTable;
