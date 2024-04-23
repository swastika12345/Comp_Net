def find_network_host(ip_address):
    ip, subnet_mask = ip_address.split('/')
    subnet_mask = int(subnet_mask)

    # Calculate network ID
    ip_parts = ip.split('.')
    network_id_parts = ip_parts[:4]
    for i in range(subnet_mask // 8):
        network_id_parts[i] = str(int(ip_parts[i]) & 255)
    for i in range(subnet_mask // 8, 4):
        network_id_parts[i] = '0'
    network_id = '.'.join(network_id_parts)

    # Calculate host ID
    host_id_parts = ip_parts[:4]
    for i in range(subnet_mask // 8, 4):
        host_id_parts[i] = str(int(ip_parts[i]) & 255)
    host_id = '.'.join(host_id_parts)

    return network_id, host_id

# Example usage
ip_address = input("Enter Classless IP Address in the format (64.0.0.8/24):")
network_id, host_id = find_network_host(ip_address)
print("Network ID:", network_id)
print("Host ID:", host_id)