require 'test/unit'

class MyTest < Test::Unit::TestCase

  # Called before every test method runs. Can be used
  # to set up fixture information.
  def setup
    require_relative '../parse_info'
    @data1 = ParseInfo.new('../Json/test.json')
    create_route_list()
    assert(@data1.keys.size == 6)
  end

  def create_route_list
    ports =[["CMI","NYC"],["NYC","MEX"],["MEX","CMI"],["MEX","LAX"],["LAX","CHI"],["CHI","ATL"],["ATL","LAX"],["ATL","CMI"]]
    distances = [2736, 2955, 2518, 1757, 785, 975, 831, 1208]
    @routes = Hash[]
    @keys = Array.new(0)
    require_relative '../edit_data'
    @edit = Edit_Data.new

    for i in 0..ports.size - 1
      route = Hash[]
      route["ports"] = ports[i]
      route["distance"] = distances[i]
      if @routes[ports[i][0]] == nil
        @routes[ports[i][0]] = Array.new(0)
        @keys.push(ports[i][0])
      end
      @routes[ports[i][0]].push(route)

    end
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.

  def teardown
    cmp_routes

    rm_routes

    add_routes
  end

  def add_routes
    add_route("ATL", "CMI", 1209)
    cmp_routes

    add_route("CHI", "NYC", 1209)
    cmp_routes
  end

  def rm_routes
    rm_route("ATL", "CMI")
    cmp_routes

    rm_route("NYC", "MEX")
    cmp_routes
  end

  def cmp_routes
    for i in 0..@keys.size - 1
        test_list = @routes[@keys[i]]
        program_list = @data1.metros[@keys[i]][CONNECTION_LIST]
        assert(test_list.size == program_list.size)
      for i in 0..test_list.size - 1
        found = false
        for i in 0..program_list.size - 1
          if test_list[i]["ports"][0] == program_list[i].departure_port
            assert(test_list[i]["ports"][1] == program_list[i].destination_port)
            assert(test_list[i]["distance"] == program_list[i].distance)
            found = true
          end
          assert(found)
        end
      end

    end
  end

  def rm_route(dep, dest)
    @edit.remove_route(@data1, dep, dest)
    list = @routes[dep]
    for i in 0..list.size - 1
      if list[i]["ports"][1] == dest
        list.delete_at(i)
      end
    end
  end

  def add_route(dep, dest, distance)
    route = Hash[]
    ports = [dep, dest]
    route["ports"] = ports
    route["distance"] = distance
    @routes[dep].push(route)

    @edit.add_route(@data1, dep  , dest, distance)
  end

  # Fake test
  def test_fail

  end
end