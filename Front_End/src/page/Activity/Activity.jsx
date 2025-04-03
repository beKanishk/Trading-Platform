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
import { Button } from '@/components/ui/button';
import { useDispatch, useSelector } from 'react-redux';
import { getAllOrdersForUser } from '@/State/Order/Action';
import { calculateProfit } from '@/Utils/CalculateProfit';

export const Activity = () => {
  const dispatch = useDispatch()
  const {order} = useSelector(store => store)

  useEffect(() => {
    dispatch(getAllOrdersForUser({jwt: localStorage.getItem("jwt")}))
  }, [])

  return (
    <div className='p-5 lg:px-20'>
        <h1 className='font-bold text-3xl pb-5'>Activity</h1>
        <Table className="border">
          <TableHeader>
            <TableRow>
              <TableHead className="w-[150px] text-center py-5">Date & Time</TableHead>
              <TableHead className="text-center py-5">Trading Pair</TableHead>
              <TableHead className="text-center py-5">Buy Price</TableHead>
              <TableHead className="text-center py-5">Sell Price</TableHead>
              <TableHead className="text-center py-5">Order Type</TableHead>
              <TableHead className="text-center py-5">Profit/Loss</TableHead>
              <TableHead className="text-center py-5">Value</TableHead>
            </TableRow>
          </TableHeader>

      <TableBody>
        {order.orders.map((item, index) => (
          <TableRow key={index} className="border-b">
            <TableCell className="text-center">
              <p>2025/03/12</p>
              <p className='text-gray-400'>12:19:45</p>
            </TableCell>
            <TableCell className="font-medium items-center gap-2 text-center">
              <Avatar className="-z-50">
                <AvatarImage src={item.orderItem.coin.image} alt="bitcoinimage" />
              </Avatar>
              <span>{item.orderItem.coin.name}</span>
            </TableCell>
            <TableCell className="text-center">${item.orderItem?.buyPrice}</TableCell>
            <TableCell className="text-center">{item.orderItem?.sellPrice == 0 ? "-" : "$"+item.orderItem?.sellPrice}</TableCell>
            <TableCell className="text-center">{item.orderType}</TableCell>
            <TableCell className="text-center">{calculateProfit(item)}</TableCell>
            <TableCell className="text-center">${(item.orderItem.quantity) * (item.orderItem.coin.current_price)}</TableCell>
            
          </TableRow>
        ))}
      </TableBody>
    </Table>

      </div>
  )
}
