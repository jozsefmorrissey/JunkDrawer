class Route
  attr_accessor :departure_port, :destination_port, :distance

  def initialize(route)
    @departure_port = route["ports"][0]
    @destination_port = route["ports"][1]
    @distance = route["distance"]
    @flight_time = get_flight_time
  end

  def get_flight_time
    if distance < 400
      peak = distance/2
      return peak*((peak*750.0)/200.0)
    end
  end
end