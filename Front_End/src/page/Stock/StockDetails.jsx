import { Avatar, AvatarImage } from '@/components/ui/avatar'
import { Button } from '@/components/ui/button'
import { Bookmark, DotIcon } from 'lucide-react'
import React, { useEffect } from 'react'
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import TradingForm from './TradingForm'
import StockChart from '../Home/StockChart'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'
import { fetchCoinDetails } from '@/State/Coin/Action'
import { store } from '@/State/Store'
import { addItemToWatchList, getUserWatchList } from '@/State/WatchList/Action'
import { existInWatchList } from '@/Utils/ExistInWatchList'

const StockDetails = () => {
  const {coin} = useSelector(store => store)
  const {watchlist} = useSelector(store => store)

  const dispatch = useDispatch()
  const {id} = useParams()
  console.log("coin:",coin)
  useEffect(() => {
    dispatch(fetchCoinDetails({coinId: id, jwt: localStorage.getItem("jwt")}))
    dispatch(getUserWatchList(localStorage.getItem("jwt")))
  }, [id])
  
  const handleAddToWatchList = () => {
    dispatch(addItemToWatchList({coinId: coin.coinDetails.id, jwt: localStorage.getItem("jwt")}))
  }

  return (
    <div className='p-5 mt-5'>

      {/* Stock Info Section */}
      <div className='flex items-center justify-between'>  
        <div>
          {/* Left Side - Avatar and Stock Details */}
          <div className='flex items-center gap-4'>
            <Avatar>
              <AvatarImage src={
                coin.coinDetails?.image.large
              } alt="Bitcoin Logo" />
            </Avatar>

            <div>
              {/* Stock Name */}
              <div className='flex items-center gap-2'>
                <p>{coin.coinDetails?.symbol.toUpperCase()}</p>
                <DotIcon className='text-gray-400' />
                <p className='text-gray-400'>{coin.coinDetails?.name}</p>
              </div>

              {/* Stock Price */}
              <div className='flex items-end gap-2'>
                <p className='text-xl font-bold'>{coin.coinDetails?.market_data.current_price.usd}</p>
                <p className='text-red-600'>
                  <span>{coin.coinDetails?.market_data.market_cap_change_24h}</span>
                  <span>({coin.coinDetails?.market_data.market_cap_change_percentage_24h}%)</span>
                </p>
              </div>
            </div>
          </div>
        </div>
        

        {/* Right Side - Buttons */}
        <div className="flex items-center gap-4"> {/* Added gap between buttons */}
          <Button onClick = {handleAddToWatchList}> 
          {existInWatchList(watchlist.items, coin.coinDetails) ? (<Bookmark className='text-white fill-current h-6 w-6'/>) : <Bookmark className='h-6 w-6'/>}
          </Button>

          <Dialog>
            <DialogTrigger>
              <Button size={"lg"}>Trade</Button>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>How much do you want to spend?</DialogTitle>
              </DialogHeader>
              <TradingForm/>
            </DialogContent>
          </Dialog>
        </div>

      </div>
      <div className='mt-14'>
        <StockChart coinId={id}/>
      </div>
      
    </div>
  )
}

export default StockDetails
