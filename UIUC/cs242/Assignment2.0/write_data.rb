class Write_Data
  require_relative('Hash_Constants')

  def initialize(airline_data, file_name)
    metros = airline_data.metros
    data_sorces = airline_data.data_sources
    keys = airline_data.keys
    @route_list_list = Array.new(0)
    met_arry = generate_met_array(metros, keys)
    routes =generate_route_array
    require 'json'
    tempHash = {
        "data_sources" => data_sorces,
        "metros" => met_arry,
        "routes" => routes
    }
    File.open(file_name,"w") do |f|
      f.write(JSON.pretty_generate(tempHash))
    end
  end


  def generate_met_array(metros, keys)
    retVal = Array.new(keys.size)
    for key_index in 0..keys.size - 1
      metro = metros[keys[key_index]]
      metro_hash = Hash[]
      metro_hash[CODE] = metro[CODE]
      metro_hash[NAME] = metro[NAME]
      metro_hash[COUNTRY] = metro[COUNTRY]
      metro_hash[CONTINENT] = metro[CONTINENT]
      metro_hash[TIMEZONE] = metro[TIMEZONE]
      metro_hash[COORDINATES] = metro[COORDINATES]
      metro_hash[POPULATION] = metro[POPULATION]
      metro_hash[REGION] = metro[REGION]

      @route_list_list.push(metro[CONNECTION_LIST])

      retVal[key_index] = metro_hash
    end
    retVal
  end

  def generate_route_array
    retVal = Array.new(0)
    for list_index in 0..@route_list_list.size - 1
      route_hash = Hash[]
      list = @route_list_list[list_index]
      for route_index in 0..list.size - 1
        route = list[route_index]
        port_arr = [route.departure_port, route.destination_port]
        route_hash[PORTS] = port_arr
        route_hash[DISTANCE] = list[route_index].distance
      end
      retVal.push(route_hash)
    end
    retVal
  end


end