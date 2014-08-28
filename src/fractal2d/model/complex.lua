Complex = {r = 0, i = 0}
function Complex:new(o)
	o = o or {}
	setmetatable(o,self)
	self.__index = self
	return o
end

function Complex.__add(c1, c2) 
	return Complex:new({r = c1.r + c2.r,i = c1.i + c2.i})
end

function Complex.__sub(c1, c2)
	return Complex:new({r = c1.r - c2.r,i = c1.i - c2.i})
end

function Complex.__mul(c1, c2)
	return Complex:new({r = c1.r * c2.r - c1.i * c2.i, i = c1.r * c2.i + c1.i * c2.r})
end

function Complex.__pow(c, exp)
	if (exp == 0) then return 1 end
	result = c
	for i = 2, exp do
		result = result * c
	end
	return result
end

function Complex.__div(c1, c2)
	r = (c1.r * c2.r + c1.i * c2.i) / (c2.r^2 + c2.i^2)
	i = (c1.i * c2.r - c1.r * c2.i) / (c2.r^2 + c2.i^2)
	return Complex:new({r = r, i = i})
end

function Complex.__tostring(c) 
	if c.i < 0 then
		return ""..c.r..""..c.i.."i"
	else
		return ""..c.r.."+"..c.i.."i"
	end
end
