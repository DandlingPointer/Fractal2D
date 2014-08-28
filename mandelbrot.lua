z = Complex:new()
c = Complex:new({r=x, i=y})

for i = 0, 20 do
	 z = z^2 + c
	 if (z:absolute() >= 2) then
		 r = 1.0
		 g = 1.0
		 b = (i-0)/(20-0) * (1.0-0.0) + 0.0
		 return
	 end
end
r = 0.0
g = 0.0
b = 0.0
