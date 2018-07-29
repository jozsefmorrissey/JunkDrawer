require 'test/unit'

class MyTest < Test::Unit::TestCase

  # Called before every test method runs. Can be used
  # to set up fixture information.
  def setup
    require_relative '../parse_info'
    data = ParseInfo.new('../Json/json')

    require_relative '../airline_data'
    @airlineData = AirlineData.new(data.data_sources, data.metros, data.keys)

    @airlineData.add_data("../Json/alternate.json")
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.

  def teardown
    assert(@airlineData.metros["CMI"] != nil)
  end

  # Fake test
  def test_fail

  end
end