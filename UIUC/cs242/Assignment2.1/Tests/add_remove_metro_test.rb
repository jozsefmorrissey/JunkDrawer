require 'test/unit'

class MyTest < Test::Unit::TestCase

  # Called before every test method runs. Can be used
  # to set up fixture information.
  def setup

    require_relative '../parse_info'
    @data1 = ParseInfo.new('../Json/test.json')

    create_terminal_list()
  end

  def check_new_terminal
    metro = @data1.metros[@new_terminal[CODE]]
    assert(@new_terminal[NAME] == metro[NAME])
    assert(@new_terminal[COUNTRY] == metro[COUNTRY])
    assert(@new_terminal[CONTINENT] == metro[CONTINENT])
    assert(@new_terminal[TIMEZONE] == metro[TIMEZONE])
    assert(@new_terminal[POPULATION] == metro[POPULATION])
    assert(@new_terminal[REGION] == metro[REGION])
    assert(@new_terminal[COORDINATES][@dir1] == metro[COORDINATES][@dir1])
    assert(@new_terminal[COORDINATES][@dir2] == metro[COORDINATES][@dir2])
  end

  def check_no_routes(code)
    metros = @data1.metros
    keys = @data1.keys
    for index in 0..keys.size - 1
      route_list = metros[keys[index]][CONNECTION_LIST]
      for list_index in 0..route_list.size- 1
        route = route_list[list_index]
        assert(route.departure_port != code)
        assert(route.destination_port != code)
      end
    end
  end

  def check_terminals
    for index in 0..@terminal_list.size - 1
      assert(@data1.metros[@terminal_list[index]] != nil)
    end
  end

  def create_terminal_list
    @terminal_list = ["ATL", "CHI", "MEX", "NYC", "LAX", "CMI"]
    @new_terminal = Hash[]
    @new_terminal[CODE] = "PQP"
    @new_terminal[NAME] = "Salad Dressing"
    @new_terminal[COUNTRY] = "GA"
    @new_terminal[CONTINENT] = AFRICA
    @new_terminal[TIMEZONE] = -8
    coordinates = Hash[]
    @dir1 = "S"
    @dir2 = "W"
    coordinates[@dir1] = 55
    coordinates[@dir2] = 12
    @new_terminal[COORDINATES] = ""
    @new_terminal[POPULATION] = 118432
    @new_terminal[REGION] = 1
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.
  def teardown
    check_terminals()

    term = "CHI"
    require_relative '../edit_data'
    edit = Edit_Data.new
    edit.rm_terminal(@data1, term)
    check_no_routes(term)

    @terminal_list.delete_at(1)
    check_terminals

    edit.set_new_terminal(@data1, @new_terminal[CODE], @new_terminal[CONTINENT], @new_terminal[COORDINATES], @new_terminal[COUNTRY], @new_terminal[NAME], @new_terminal[POPULATION], @new_terminal[REGION], @new_terminal[TIMEZONE])

    @terminal_list.push(@new_terminal[CODE])
    check_terminals
    check_new_terminal()

  end

  # Fake test
  def test_fail

  end
end