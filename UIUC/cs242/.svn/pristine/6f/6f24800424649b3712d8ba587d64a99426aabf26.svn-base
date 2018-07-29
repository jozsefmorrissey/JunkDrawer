require 'test/unit'
require_relative '../Hash_Constants'

class MyTest < Test::Unit::TestCase


      # Called before every test method runs. Can be used
  # to set up fixture information.
  def setup
    # Do nothing
    require_relative '../parse_info'
    @data1 = ParseInfo.new('../Json/test.json')

    require_relative('../write_data')
    Write_Data.new(@data1, "../Json/temp.json")

    @data2 = ParseInfo.new('../Json/temp.json')

  end

  def lax_data
    lax_hash = Hash[]
    lax_hash[CODE] = "LAX"
    lax_hash[NAME] = "Los Angeles"
    lax_hash[COUNTRY] = "US"
    lax_hash[CONTINENT] = "North America"
    lax_hash[TIMEZONE] = -8
    lax_hash[COORDINATES] = Hash[]
    lax_hash[COORDINATES]["N"] = 34
    lax_hash[COORDINATES]["W"] = 118
    lax_hash[POPULATION] = 17900000
    lax_hash[REGION] = 1
    lax_hash
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.

  def teardown
    # Do nothing
  end

  # Fake test
  def test_fail
    check_data(@data1)
    check_data(@data2)
  end

  def check_data(data)
    lax_hash = lax_data
    assert(lax_hash[CODE] == data.metros["LAX"][CODE])
    assert(lax_hash[NAME] == data.metros["LAX"][NAME])
    assert(lax_hash[COUNTRY] == data.metros["LAX"][COUNTRY])
    assert(lax_hash[CONTINENT] == data.metros["LAX"][CONTINENT])
    assert(lax_hash[TIMEZONE] == data.metros["LAX"][TIMEZONE])
    assert(lax_hash[COORDINATES]["N"] == data.metros["LAX"][COORDINATES]["N"])
    assert(lax_hash[COORDINATES]["W"] == data.metros["LAX"][COORDINATES]["W"])
    assert(lax_hash[POPULATION] == data.metros["LAX"][POPULATION])
    assert(lax_hash[REGION] == data.metros["LAX"][REGION])
    assert(data.metros["MEX"] != nil)
    assert(data.metros["CMI"] != nil)
    assert(data.metros["CHI"] != nil)
    assert(data.metros["NYC"] != nil)
    assert(data.metros["ATL"] != nil)
  end
end