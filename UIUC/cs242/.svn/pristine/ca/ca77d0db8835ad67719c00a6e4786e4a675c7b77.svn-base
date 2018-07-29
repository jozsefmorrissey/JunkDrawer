require 'test/unit'

class MyTest < Test::Unit::TestCase

  # Called before every test method runs. Can be used
  # to set up fixture information.
  def setup
    require_relative '../parse_info'
    @data1 = ParseInfo.new('../Json/shortest_path.json')
    require_relative '../flight_plan'
    @flight_plan = Flight_Plan.new(@data1)
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.

  def teardown
    fp = @flight_plan.shortest_path(@data1, @data1.metros["LAX"], "MEX", 0)
    assert(fp.size == 5)

    require_relative '../edit_data'
    edit = Edit_Data.new
    edit.remove_route(@data1, "LAX", "MEX")
    edit.add_route(@data1, "LAX", "MEX", 5999)

    fp = @flight_plan.shortest_path(@data1, @data1.metros["LAX"], "MEX", 0)
    assert(fp.size == 1)
  end

  # Fake test
  def test_fail

  end
end