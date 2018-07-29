require 'test/unit'

class ModifyMetroTest < Test::Unit::TestCase

  # Called before every test method runs. Can be used
  # to set up fixture information.


  def setup

    require_relative '../parse_info'
    @data1 = ParseInfo.new('../Json/test.json')
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.

  def teardown
    require_relative '../edit_data'
    @edit = Edit_Data.new
    puts "cont\n"
    lax_characteristics()

    code = change_code
    change_name(code)
    change_country(code)
    change_continent(code)
    change_timezone(code)
    change_coordinates(code)
    change_population(code)
    change_region(code)
  end

  def change_region(code)
    @edit.change_region(@data1.metros[code], "2")
    @lax_hash[REGION] = 2
    check_data(code)

    @edit.change_region(@data1.metros[code], "-2")
    check_data(code)
  end

  def change_population(code)
    @edit.change_population(@data1.metros[code], "123456")
    @lax_hash[POPULATION] = 123456
    check_data(code)

    @edit.change_population(@data1.metros[code], "-4")
    check_data(code)
  end

  def change_coordinates(code)
    @edit.change_coordinates(@data1.metros[code], "N 19,W 91")
    @dir1 = "N"
    @dir2 = "W"
    @lax_hash[COORDINATES][@dir1] = 19
    @lax_hash[COORDINATES][@dir2] = 91
    check_data(code)

    @edit.change_coordinates(@data1.metros[code], "N 9, W 1")
    @lax_hash[COORDINATES][@dir1] = 19
    @lax_hash[COORDINATES][@dir2] = 91
    check_data(code)

    @edit.change_coordinates(@data1.metros[code], "s 88,e 16")
    @dir1 = "S"
    @dir2 = "E"
    @lax_hash[COORDINATES][@dir1] = 88
    @lax_hash[COORDINATES][@dir2] = 16
    check_data(code)
  end

  def change_timezone(code)
    @edit.change_timezone(@data1.metros[code], "-3")
    @lax_hash[TIMEZONE] = -3
    check_data(code)

    @edit.change_timezone(@data1.metros[code], "-12")
    check_data(code)

    @edit.change_timezone(@data1.metros[code], "15")
    check_data(code)

    @edit.change_timezone(@data1.metros[code], "3")
    @lax_hash[TIMEZONE] = 3
    check_data(code)
  end

  def change_continent(code)
    @edit.change_continent(@data1.metros[code], "aFriCa")
    @lax_hash[CONTINENT] = AFRICA
    check_data(code)

    @edit.change_continent(@data1.metros[code], "south america")
    @lax_hash[CONTINENT] = SOUTH_AMERICA
    check_data(code)

    @edit.change_continent(@data1.metros[code], "north america")
    @lax_hash[CONTINENT] = NORTH_AMERICA
    check_data(code)

    @edit.change_continent(@data1.metros[code], "asia")
    @lax_hash[CONTINENT] = ASIA
    check_data(code)

    @edit.change_continent(@data1.metros[code], "eurOpe")
    @lax_hash[CONTINENT] = EUROPE
    check_data(code)

    @edit.change_continent(@data1.metros[code], "australia")
    @lax_hash[CONTINENT] = AUSTRALIA
    check_data(code)

    @edit.change_continent(@data1.metros[code], "anTarcTica")
    @lax_hash[CONTINENT] = ANTARCTICA
    check_data(code)

    @edit.change_continent(@data1.metros[code], "mars")
    @lax_hash[CONTINENT] = ANTARCTICA
    check_data(code)
  end

  def change_country(code)
    @edit.change_country(@data1.metros[code], "nWp")
    @lax_hash[COUNTRY] = "NWP"
    check_data(code)
  end

  def change_name(code)
    @edit.change_name(@data1.metros[code], "hEllO kiTTY")
    @lax_hash[NAME] = "Hello Kitty"
    check_data(code)
  end

  def change_code
    code = "PQP"
    @edit.change_code(@data1.metros["LAX"], "pQp", @data1)
    @lax_hash[CODE] = code
    check_data(code)
    assert(@data1.metros["LAX"] == nil)

    @edit.change_code(@data1.metros["LAX"], "pQ", @data1)
    check_data(code)
    assert(@data1.metros["LAX"] == nil)
    code

    @edit.change_code(@data1.metros["LAX"], "pQpq", @data1)
    check_data(code)
    assert(@data1.metros["LAX"] == nil)
    code

    code
  end

  def lax_characteristics
      @lax_hash = Hash[]
      @lax_hash[CODE] = "LAX"
      @lax_hash[NAME] = "Los Angeles"
      @lax_hash[COUNTRY] = "US"
      @lax_hash[CONTINENT] = "North America"
      @lax_hash[TIMEZONE] = -8
      @lax_hash[COORDINATES] = Hash[]
      @lax_hash[COORDINATES]["N"] = 34
      @lax_hash[COORDINATES]["W"] = 118
      @lax_hash[POPULATION] = 17900000
      @lax_hash[REGION] = 1
  end

  # Fake test
  def test_fail

  end

  def check_data(code)
    assert(@lax_hash[CODE] == @data1.metros[code][CODE])
    assert(@lax_hash[NAME] == @data1.metros[code][NAME])
    assert(@lax_hash[COUNTRY] == @data1.metros[code][COUNTRY])
    assert(@lax_hash[CONTINENT] == @data1.metros[code][CONTINENT])
    assert(@lax_hash[TIMEZONE] == @data1.metros[code][TIMEZONE])
    assert(@lax_hash[COORDINATES][@dir1] == @data1.metros[code][COORDINATES][@dir1])
    assert(@lax_hash[COORDINATES][@dir2] == @data1.metros[code][COORDINATES][@dir2])
    assert(@lax_hash[POPULATION] == @data1.metros[code][POPULATION])
    assert(@lax_hash[REGION] == @data1.metros[code][REGION])
    assert(@data1.metros[code] != nil)
    assert(@data1.metros[code] != nil)
    assert(@data1.metros[code] != nil)
    assert(@data1.metros[code] != nil)
    assert(@data1.metros[code] != nil)
  end



end