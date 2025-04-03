import React, { useEffect } from 'react'
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { useDispatch, useSelector } from 'react-redux';
import { getUserAssets } from '@/State/Asset/Action';

const Portfolio = () => {
  const dispatch = useDispatch();
  const {asset} = useSelector(store => store)

  useEffect(() => {
    dispatch(getUserAssets(localStorage.getItem("jwt")));
  }, []);

  return (
    <div className='p-5 lg:p-20'>
      <h1 className='font-bold text-3xl pb-5'>Portfolio</h1>
      <Table className="w-full border-collapse">
        <TableHeader>
          <TableRow>
            <TableHead className="w-[150px] text-center">Asset</TableHead>
            <TableHead className="text-center">Symbol</TableHead>
            <TableHead className="text-center">Unit</TableHead>
            <TableHead className="text-center">Change</TableHead>
            <TableHead className="text-center">Change%</TableHead>
            <TableHead className="text-right">Volume</TableHead>
          </TableRow>
        </TableHeader>

        <TableBody>
          {asset.userAssets?.map((item, index) => (
            <TableRow key={index} className="border-b">
              <TableCell className="font-medium flex items-center gap-2 text-center">
                <Avatar className="-z-50">
                  <AvatarImage src={item.coin.image || ""} alt={item.coin.name} />
                </Avatar>
                <span>{item.coin.name}</span>
              </TableCell>
              <TableCell className="text-center">{item.coin.symbol.toUpperCase()}</TableCell>
              <TableCell className="text-center">{item.quantity}</TableCell>
              <TableCell className="text-center">{item.coin.market_cap_change_24h}</TableCell>
              <TableCell className="text-center">% {item.coin.market_cap_change_percentage_24h}</TableCell>
              <TableCell className="text-right">{item.coin.total_volume}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}

export default Portfolio;
