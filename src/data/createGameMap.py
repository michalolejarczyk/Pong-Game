import os



file = open(os.path.join(os.getcwd(), "map.txt"),'w')

file.write("! 8 = tiletop\n")
file.write("! 2 = tilebottom\n")

file.write("8"*20)

for i in range(10):
    file.write("\n")

file.write("\n"+"2"*20)

file.close()
print("done")