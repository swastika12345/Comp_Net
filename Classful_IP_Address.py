def findClass(ip):
    try:
        ip=ip.split(".")
        ip=[int(i) for i in ip]
        if ip[0] >=0 and ip[0] <= 127:
            return "A"
        elif ip[0] >=128 and ip[0] <= 191:
            return "B"
        elif ip[0] >= 192 and ip[0] <= 223:
            return "C"
        elif ip[0] >= 224 and ip[0] <= 239:
            return "D"
        else:
            return "E"

    except:
        return "Invalid IP Address format"
def sep(ip,className):
        if className == "A":
            print("Network ID :",ip[0],)
            print("Host ID :",".".join(ip[1:4]))
        elif className == "B":
            print("Network ID :",".".join(ip[0:2]))
            print("Host ID :",".".join(ip[2:4]))
        elif className == "C":
            print("Network ID :",".".join(ip[0:3]))
            print("Host ID :",ip[3])
        elif className == "D":
            print("Network ID :",".".join(ip[0:4]))
        else:
            print("In this class,IP Address can't divide into network/Host Address")
            

#Driver Code
ip=input("Enter IP Address(Classful):")
networkClass=findClass(ip)
print("Given IP Address belongs to Class:",networkClass)
if networkClass != "Invalid IP Address Format":
    ip=ip.split(".")
    sep(ip,networkClass)
