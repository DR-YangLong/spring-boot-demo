local function rm()
local i=0.01
local o=redis.call("HGET","bonus:amount",KEYS[1])
local a=redis.call("HGET","group:size",KEYS[1])
local p=redis.call("HGET","bonus:num",KEYS[1])
p=a-p
if (p<=1) then
if(p==1) then
redis.call("HSET","bonus:amount",KEYS[1],0)
redis.call("HINCRBY","bonus:num",KEYS[1],1)
return o .. "_" .. 0
else
return 0 .. "_" .. 0
end
else
local s=o/p*2
for l=1,3 do
math.random()
if l==3 then
s=math.random()*s
end
end
s=s<0.01 and 0.01 or s
s=math.floor(s*100)/100
o=o-s
if o<0 then
s=s+o
o=0
end
redis.call("HSET","bonus:amount",KEYS[1],o)
redis.call("HINCRBY","bonus:num",KEYS[1],1)
return s.. "_" .. p-1
end
end
return rm()