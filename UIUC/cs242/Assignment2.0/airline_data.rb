#file: airline_data.rb
require_relative('Hash_Constants')

class AirlineData
  attr_accessor :data_sources, :metros, :longest_route, :shortest_route, :population_average, :keys

  #Key words used to determine which functions are to be called
  MAX_DISTANCE = "max_distance"
  MIN_DISTANCE = "min_distance"
  MAX = "max"
  MIN = "min"

  #Key for sort comparators
  MOST_CONNECTIONS = "most_connections"
  ALPHABETICAL = "a_to_z"

=begin
  a list of the continents served by CSAir and which cities are in them
  identifying CSAir's hub cities – the cities that have the most direct connections.

  Searches through all metros (Level 0)
  attribute is the attribute is a keyword used to determine which attribute is being evaluated
  comparator is a keyword used to determine which comparison function to use
=end
  def find_extrema(initVal, attribute, comparator)
    base = initVal
    for key_index in 0..@keys.size - 1
      metro = @metros[@keys[key_index]]
      if metro != nil
        base = get_level_0_comparator(metro, attribute, base, comparator)
      end
    end
    base
  end

  #Returns a comparison function
  def get_level_0_comparator(metro, attribute, base, cmp)
    attr = get_attribute(attribute, metro)
    case attribute
      when CONNECTION_LIST
        return cmp_list(attr, base, cmp)
      else
        return compare_element(cmp, attr, base)
    end
  end

  def get_attribute(attr, metro)
    case attr
      when POPULATION
        return metro[POPULATION]
      when CITY_INFO
        return Hash[NAME => metro["name"], POPULATION => metro["population"]]
      when CONNECTION_LIST
        return metro[CONNECTION_LIST]
    end
  end

  #Returns comparators strictly for a single element
  def compare_element(comparator, element, base)
    case comparator
      when MAX_DISTANCE
        return max_distance(element, base)
      when MIN_DISTANCE
        return  min_distance(element, base)
      when MAX
        return max(element, base)
      when MIN
        return min(element, base)
    end
  end


  def cmp_list(list, base, comparator)
    for list_index in 0..list.size - 1
      if list[list_index] != nil and compare_element(comparator ,list[list_index], base)
        base = list[list_index]
      end
    end
    base
  end

  def max(current, base)
    if current != nil
     if current[POPULATION] > base[POPULATION]
        current
      else
        base
     end
    end
  end

  def min(current, base)
    if current[POPULATION] < base[POPULATION]
      current
    else
      base
    end
  end

  def max_distance(current, base)
    current != nil and current.distance > base.distance
  end

  def min_distance(current, base)
    current != nil and current.distance < base.distance
  end

  def longest_flight()
    metro = @metros[@keys[0]]
    #puts metro[CONNECTION_LIST]
    base = metro[CONNECTION_LIST][0]
    @longest_route = self.find_extrema(base, CONNECTION_LIST, MAX_DISTANCE)
  end

  def shortest_flight()
    metro = @metros[@keys[0]]
    base = metro[CONNECTION_LIST][0]
    @shortest_route = self.find_extrema(base, CONNECTION_LIST, MIN_DISTANCE)
  end

  def population_max()
    metro = @metros[@keys[0]]
    base = Hash[NAME => metros["name"], POPULATION => metro["population"]]
    @largest_population = self.find_extrema(base, CITY_INFO, MAX)
  end

  def population_min()
    metro = @metros[@keys[0]]
    base = Hash[NAME => metros["name"], POPULATION => metro["population"]]
    @smallest_population = self.find_extrema(base, CITY_INFO, MIN)
  end

  def average(attribute)
    @population_average = 0
    count = 0
    for key_index in 0..@keys.size - 1
      if @metros[@keys[key_index]] != nil
        @population_average += @metros[@keys[key_index]][attribute]
        count += 1
      end
    end
    @population_average = @population_average / count
  end

  def average_route_length()
    @average_route_length = 0
    count = 0
    for key_index in 0..@keys.size - 1
      metro = @metros[@keys[key_index]]
      if metro != nil
        list = metro[CONNECTION_LIST]
        for index in 0..list.size - 1
          if list[index] != nil && list[index].distance != nil
            @average_route_length += list[index].distance
            count += 1
          end
        end
      end
    end
    @average_route_length = @average_route_length / count
  end

  def continent_list
    for key_index in 0..@keys.size - 1
        metro = @metros[@keys[key_index]]
          @continents_serviced[metro[CONTINENT]].push(metro[NAME])
    end
  end

    def init_continent_list
      @continents_serviced = Hash[]
      for index in 0..CONTINENT_KEYS.size - 1
        list = Array.new(0)
        @continents_serviced[CONTINENT_KEYS[index]] = list
      end
    end


  def get_sort_cmp(cmp, list, current, base)
    case cmp
      when MOST_CONNECTIONS
        return connections(list, current, base)
      when ALPHABETICAL
        return a_to_z(list, current, base)
    end
  end

  def bubble_sort(cmp, list)
    for current_index in 0..list.size - 1
      min = find_min(current_index, list, cmp)
      temp = list[current_index]
      list[current_index] = list[min]
      list[min] = temp
      #puts current_index
    end
  end

  def find_min(current_index, list, cmp)
    min = current_index
    for index in current_index + 1..list.size-1
      min = get_sort_cmp(cmp, list, min, index)
    end
    min
  end

    def connections(list, min, cur)
      metro_min = @metros[list[min]]
      metro_cur = @metros[list[cur]]

      if metro_min[CONNECTION_LIST].size < metro_cur[CONNECTION_LIST].size
        return cur
      else
        return min
      end
    end

  def a_to_z(list, min, cur)
    if list[min] > list[cur]
      return  cur
    else
      return min
    end
  end

  def sort_by_most_connections(list)
    bubble_sort(MOST_CONNECTIONS, list)
  end

  def sort_alphabetically(list)
    bubble_sort(ALPHABETICAL, @keys)
  end

  def add_data(filename)
    parse_info = ParseInfo.new(filename)

    add_sources(parse_info.data_sources)
    add_metros(parse_info.metros, parse_info.keys)
    add_keys(parse_info.keys)
  end

  def add_keys(keys)
    for i in 0..keys.size - 1
      add = true
      for j in 0..@keys.size - 1
        if @keys[j] == keys[i]
          add = false
          break
        end
      end
      if add
        @keys.push(keys[i])
      end
    end
  end

  def add_sources(dat)
    for i in 0..dat.size - 1
      add = true
      for j in 0..@data_sources.size - 1
        if @data_sources[j] == dat[i]
          add = false
          break
        end
      end
      if add
        @data_sources.push(dat[i])
      end
    end
  end

  def add_metros(metros, keys)
    for i in 0..keys.size - 1
      code = keys[i]
      metro = metros[code]
      add = true
      for j in 0..@keys.size
        if @keys[j] == code
          update_metro(code, metro)
          add = false
          break
        end
      end
      if add
        @metros[code] = metro
      end
    end
  end

  def update_metro(code, metro)
    @metros[code][POPULATION] = metro[POPULATION]
    @metros[code][NAME] = metro[NAME]
    @metros[code][COORDINATES] = metro[COORDINATES]
    @metros[code][REGION] = metro[REGION]
    @metros[code][COUNTRY] = metro[COUNTRY]
    @metros[code][CONTINENT] = metro[CONTINENT]
    @metros[code][TIMEZONE] = metro[TIMEZONE]

    new_connections = metro[CONNECTION_LIST]
    for i in 0..new_connections.size
      @metros[code][CONNECTION_LIST].push[new_connections[i]]
    end
  end

  def initialize(dat, metros, keys)
    @data_sources = dat
    @metros = metros
    @keys = keys
    analyze
  end

=begin
  Analyzes data and updates statistics
=end
  def analyze
    self.longest_flight
    self.shortest_flight
    self.population_max
    self.population_min
    self.average(POPULATION)
    self.average_route_length
    self.init_continent_list
    self.continent_list
    sort_by_most_connections(@keys)
  end
end