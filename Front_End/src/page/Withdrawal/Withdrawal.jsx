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
import { getWithdrawalHistory } from '@/State/Withdrawal/Action';


const Withdrawal = () => {
  const dispatch = useDispatch()
  const {wallet, withdrawal} = useSelector(store => store)

  useEffect(() => {
    dispatch(getWithdrawalHistory({jwt: localStorage.getItem("jwt")}))
  },[])

  return (
    <div className='p-5 lg:px-20'>
        <h1 className='font-bold text-3xl pb-5'>Withdrawal</h1>
        <Table className="border">
          <TableHeader>
            <TableRow>
              <TableHead className="w-[150px] text-center py-5">Date</TableHead>
              <TableHead className="text-center py-5">Method</TableHead>
              <TableHead className="text-center py-5">Amount</TableHead>
              <TableHead className="text-center py-5">Status</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {withdrawal.history.map((item, index) => (
              <TableRow key={index} className="border-b">

                <TableCell className="text-center">{item.date.split("T")[0]}</TableCell>
                <TableCell className="text-center">Bank</TableCell>
                <TableCell className="text-center">${item.amount}</TableCell>
                <TableCell className="text-center">{item.withdrawalStatus}</TableCell>
                
              </TableRow>
            ))}
          </TableBody>
        </Table>

      </div>
  )
}

export default Withdrawal