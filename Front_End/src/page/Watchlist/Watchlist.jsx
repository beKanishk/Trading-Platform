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
import { Bookmark } from 'lucide-react';
import { useDispatch, useSelector } from 'react-redux';
import { addItemToWatchList, getUserWatchList } from '@/State/WatchList/Action';

const Watchlist = () => {
  const dispatch = useDispatch()
  const {watchlist} = useSelector(store => store)

  const handleRemoveToWatchlist = (value) =>{
    dispatch(addItemToWatchList({coinId: value, jwt: localStorage.getItem("jwt")}))
    console.log(value)
  }

  useEffect(() =>{
    dispatch(getUserWatchList(localStorage.getItem("jwt")))
  }, [])


  return (
    <div className='p-5 lg:px-20'>
        <h1 className='font-bold text-3xl pb-5'>Watchlist</h1>
        <Table className="border">
          <TableHeader>
            <TableRow>
              <TableHead className="w-[150px] text-center py-5">Coin</TableHead>
              <TableHead className="text-center py-5">Symbol</TableHead>
              <TableHead className="text-center py-5">Volume</TableHead>
              <TableHead className="text-center py-5">Market Cap</TableHead>
              <TableHead className="text-center py-5">24h</TableHead>
              <TableHead className="text-right py-5">Price</TableHead>
              <TableHead className="text-right text-red-600">Remove</TableHead>
            </TableRow>
          </TableHeader>

      <TableBody>
        {watchlist.items.map((item, index) => (
          <TableRow key={index} className="border-b">
            <TableCell className="font-medium flex items-center gap-2 text-center">
              <Avatar className="-z-50">
                <AvatarImage src={item.image} alt="bitcoinimage" />
              </Avatar>
              <span>{item.name}</span>
            </TableCell>
            <TableCell className="text-center">{item.symbol}</TableCell>
            <TableCell className="text-center">{item.total_volume}</TableCell>
            <TableCell className="text-center">{item.market_cap}</TableCell>
            <TableCell className="text-center">${item.price_change_24h.toFixed(5)}</TableCell>
            <TableCell className="text-right">${item.current_price}</TableCell>
            <TableCell className="text-right">
              <Button variant="ghost" onClick = {() => handleRemoveToWatchlist(item.id)} size="icon" className="h-10 w-10">
                <Bookmark className='text-white fill-current h-6 w-6'/>
              </Button>
            </TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>

      </div>
  )
}

export default Watchlist