require_relative 'Hash_Constants'
class ParseInfo
  attr_reader :metros, :routes, :data_sources, :keys
  def initialize(file_name)
      @file_name = file_name
      self.parser()
    end

  def extract_metros(my_hash)
    met = my_hash["metros"]
    size = met.count
    @metros = Hash[]
    @keys = Array.new(0)
    for i in 0..size - 1
      if met[i] != nil
        code = met[i][CODE]
        @keys.push(code)
        @metros[code] = met[i]
        connection_list = Array.new(0)
        @metros[code][CONNECTION_LIST] = connection_list
      end
    end
  end

  def extract_routes(my_hash)
    rts = my_hash["routes"]
    require_relative('route')
    size = rts.count
    @route_list_list = Array.new(0)
    for i in 0..size - 1
      if rts[i] != nil
        @route_list_list.push(Route.new(rts[i]))
      end
    end
  end

  def extract_connection_list()
    num_routes = @route_list_list.size
    for rt_index in 0..num_routes - 1
      if @route_list_list[rt_index] != nil
        route = @route_list_list[rt_index]
        @metros[route.departure_port]["connection_list"].push(@route_list_list[rt_index])
      end
    end
  end

  def parser()
    require 'json'
    file = File.read(@file_name)
    my_hash = JSON.parse(file)
    extract_data_sources(my_hash)
    extract_metros(my_hash)
    extract_routes(my_hash)
    extract_connection_list
  end

  def extract_data_sources(my_hash)
    @data_sources = my_hash["data sources"]
  end

end