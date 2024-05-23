def int_to_ip(ip_int):
    return '.'.join(str((ip_int >> i) & 0xFF) for i in (24, 16, 8, 0))

def calculate_subnets(ip_address_str, num_subnets):
    ip_parts = ip_address_str.split('.')
    ip_value = sum(int(part) << (24 - 8 * i) for i, part in enumerate(ip_parts))

    subnet_size = 2**(32 - num_subnets)
    subnet_mask = (2**32 - 1) << (32 - num_subnets)

    starting_addresses = [ip_value + i * subnet_size for i in range(num_subnets)]

    return subnet_mask, starting_addresses

# Define IP address and number of subnets required
ip_address_str = "192.168.1.0"  # Example IP address
num_subnets = 4  # Example number of subnets required

subnet_mask, starting_addresses = calculate_subnets(ip_address_str, num_subnets)

print("Subnet Mask:", int_to_ip(subnet_mask))
print("Starting Addresses of Subnets:")
for i, address in enumerate(starting_addresses):
    print(f"Subnet {i+1}: {int_to_ip(address)}")
