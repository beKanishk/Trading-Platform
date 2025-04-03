import { Button } from "@/components/ui/button";
import React, { useEffect, useState } from "react";
import AssetTable from "./AssetTable";
import StockChart from "./StockChart";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { MessageCircle, X, DotIcon } from "lucide-react";
import { Input } from "@/components/ui/input";
import { getCoinList, getTop50CoinList, getTrending } from "@/State/Coin/Action";
import { useDispatch, useSelector } from "react-redux";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import { getPaymentDetails } from "@/State/Withdrawal/Action";
import PromptMessage from "@/ChatBox/PromptMessage";
import ResponseMessage from "@/ChatBox/ResponseMessage";
import axios from "axios";
import { data } from "react-router-dom";

const Home = () => {
  const [category, setCategory] = useState("all");
  const [inputValue, setInputValue] = useState("");
  const [isBotRelease, setIsBotRelease] = useState(false);
  const dispatch = useDispatch();
  const { coin, withdrawal } = useSelector((state) => state);
  const coinList = useSelector((state) => state.coin?.coinList ?? []);
  const [response, setResponse] = useState([]);
  const [loading, setLoading] = useState(false)
  const auth = useSelector((state) => state.auth);
  
  useEffect(() => {
    dispatch(getPaymentDetails({ jwt: localStorage.getItem("jwt") }));
    console.log(JSON.parse(localStorage.getItem("paymentDetails")));
  }, []);

  useEffect(() => {
    dispatch(getTop50CoinList());
    dispatch(getTrending());
    console.log("Coin List", coin);
  }, [category]);

  useEffect(() => {
    dispatch(getCoinList(2));
  }, [category]);

  const handleChange = (e) => setInputValue(e.target.value);

  const handleKeyPress = async (prompt) => {
    setLoading(true)

    try {
      const { data } = await axios.post("http://localhost:8080/ai/chat",{prompt});
      const response = {message: data.message, role:"model"}
      //const userPrompt = {message: prompt, role:"user"}
      setResponse((prev) => [...prev, response])
      console.log("Success", data);
    } catch (error) {
      console.log("Error", error);
    }
    setLoading(false)
  };
  
  const handleBotRelease = () => setIsBotRelease(!isBotRelease);

  // Determine which coin data to display
  let coinData;
  if (category === "all") {
    coinData = coinList;
  } else if (category === "trending") {
    coinData = coin.trending;
  } else {
    coinData = coin.top50;
  }

  return (
    <div className="w-full min-h-screen">
      <div className="flex flex-col lg:flex-row w-full h-full">
        {/* Left Section (Table) */}
        <div className="w-full lg:w-1/2 lg:border-r p-4">
          <div className="flex flex-wrap items-center gap-3">
            {["all", "top50", "trending",].map((item) => (
              <Button
                key={item}
                onClick={() => setCategory(item)}
                variant={category === item ? "default" : "outline"}
                className="rounded-full px-6 py-2"
              >
                {item === "all"
                  ? "All"
                  : item === "top50"
                  ? "Top 50"
                  : item === "trending"
                  ? "Trending"
                  : "Top Losers"}
              </Button>
            ))}
          </div>
          <AssetTable coin={coinData} category={category} />
          {/* <div>
            <Pagination>
              <PaginationContent>
                <PaginationItem>
                  <PaginationPrevious href="#" />
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#">1</PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationEllipsis />
                </PaginationItem>
                <PaginationItem>
                  <PaginationNext href="#" />
                </PaginationItem>
              </PaginationContent>
            </Pagination>
          </div> */}
        </div>

        {/* Right Section (Chart + Asset Info) */}
        <div className="w-full lg:w-1/2 p-6 flex flex-col items-start">
          {/* Graph */}
          <StockChart coinId={"bitcoin"} />

          {/* Asset Info (Below Chart, Left-Aligned) */}
          <div className="mt-4 flex items-center gap-4">
            {/* Avatar */}
            <Avatar>
              <AvatarImage
                src="https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400"
                alt="Bitcoin Icon"
              />
            </Avatar>

            {/* Asset Details */}
            <div>
              <div className="flex items-center gap-2">
                <p>BTC</p>
                <DotIcon className="text-gray-400" />
                <p className="text-gray-400">Bitcoin</p>
              </div>
              <div className="flex items-end gap-2">
                <p className="text-xl font-bold">${coinList[0]?.current_price}</p>
                <p className="text-red-600">
                  <span>${coinList[0]?.price_change_24h}</span>
                  <span>({coinList[0]?.price_change_percentage_24h}%)</span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* ChatBot */}
      <section className="absolute bottom-5 right-5 z-40 flex flex-col items-end gap-2">
        {isBotRelease && (
          <div className="rounded-md w-[20rem] md:w-[25rem] lg:w-[25rem] h-[70vh] bg-slate-900">
            <div className="flex justify-between items-center border-b px-6 h-[12%]">
              <p>Chat Bot</p>
              <Button onClick={handleBotRelease} variant="ghost" size="icon">
                <X />
              </Button>
            </div>

            {/* Messages Section */}
            <div className="h-[76%] flex flex-col overflow-y-auto gap-5 px-5 py-2 scroll-container">
              {/* Initial Bot Message */}
              <div className="self-start pb-5 w-auto">
                <div className="px-5 py-2 rounded-md bg-slate-800 w-auto text-left">
                  <p>Hi, {auth.user.name}</p>
                  <p>You can ask crypto-related questions</p>
                  <p>like, price, market cap, etc.</p>
                </div>
              </div>

              {/* Sample Messages */}
              {response.map((item, index) => item.role == "user" ?
              <div className="px-5 py-2 rounded-md bg-slate-800 w-auto text-left self-end" key={index}>
                <PromptMessage message={item.message}/>
              </div> :
              <div className="px-5 py-2 rounded-md bg-slate-800 w-auto text-left self-start" key={index}>
                <ResponseMessage message={item.message}/>
                </div>)}
            </div>
            {loading && <p>fetching data from server...</p>}
            {/* Input Section */}
            <div className="h-[4rem] border-t flex items-center px-4">
            <Input
              value={inputValue} // <-- Bind inputValue here
              onChange={handleChange}
              className="w-full h-full border-none outline-none"
              placeholder="Write a message..."
              onKeyDown={(e) => {
                if (e.key === "Enter" && inputValue.trim()) { // Ensure input is not empty
                  const data = { message: inputValue, role: "user" };
                  setResponse((prev) => [...prev, data]);
                  handleKeyPress(inputValue);
                  setInputValue(""); // <-- Reset input after sending message
                }
              }}
            />

            </div>
          </div>
        )}

        {/* ChatBot Button */}
        <div className="relative w-[7rem] cursor-pointer group">
          <Button onClick={handleBotRelease} className="w-full h-[2.5rem] gap-2 items-center">
            <MessageCircle
              size={25}
              className="fill-[#1e293b] -rotate-90 stroke-none group-hover:fill-[#1a1a1a]"
            />
            <span className="text-xl">Chat</span>
          </Button>
        </div>
      </section>
    </div>
  );
};

export default Home;
