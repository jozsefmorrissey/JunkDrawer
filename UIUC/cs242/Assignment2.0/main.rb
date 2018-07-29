require_relative('Hash_Constants')
require_relative('edit_data')
class Main
  #Key words used to determine previous menu when receiving input
  MAIN_MENU = "main_menu"
  LIST_COVERAGE = "list_coverage"
  STATS = "stats"


  # Signal.trap( "KEY_Esc" ) { printf "boo whoo" }
  def self.main_menu()
    while true
      printf "Selection:\n1\tObtain flight plan\n2\tA specific terminals information\n3\tStatistical data relating to CSAir\n4\tGenerate map\n5\tAdd file data\n\nSpace Bar\tTo exit\nS\t\tTo save\nE\t\tTo enter Edit menu\n"
      if get_input(MAIN_MENU) == "EXIT\n"
        break
      end
    end
  end

  def self.generate_map()
    header = "http://www.gcmap.com/mapui?P="
    keys = @airline_data.keys
    for key_index in 0..keys.size - 1
      if @airline_data.metros[keys[key_index]] != nil
        connection_list = @airline_data.metros[keys[key_index]][CONNECTION_LIST]
        for connect_index in 0..connection_list.size - 1
          header = header + connection_list[connect_index].departure_port + "-" + connection_list[connect_index].destination_port + ","
        end
      end
    end
    system("xdg-open", header)
  end

  def self.list_coverage()
    metros = @airline_data.metros
    print_unique_members(metros)

    puts "\n\nPress enter to go back or select a terminal to explore"
    get_input(MAIN_MENU)
  end

  def self.print_unique_members(metros)
    printed = Array.new(0)
    print = true
    count = 0
    metros = @airline_data.metros
    keys = @airline_data.keys
    @airline_data.sort_alphabetically(keys)
    for key_index in 0..keys.size - 1
      metro = metros[keys[key_index]]
      for print_index in 0..printed.size - 1
        if metro == nil or printed[print_index] == metro[NAME]
          print = false
          break
        end
      end
      if print and metro != nil
        count += 1
        code = "(#{metro[CODE]})"
        printf "%-6s%-25s", code, metro[NAME]
        if count % 5 == 0
          puts
        end
      else
        print = true
      end
    end
  end

  def self.get_terminal(term)
    term.upcase!
    if @airline_data.metros[term] != nil and @airline_data.metros[term][CODE] == term
      terminal_display(@airline_data.metros[term])
    end
  end

  def self.terminal_display(metro)
      printf "Terminal code: %s\t\tCity Name: %s\t\tCountry: %s\t\tContinent: %s\n\n", metro[CODE], metro[NAME], metro[COUNTRY], metro[CONTINENT]
      printf "Timezone: #{metro[TIMEZONE]}\tCoordianates: %s \n", generate_coordinates_string(metro[COORDINATES])
      printf "Population: #{metro[POPULATION]} \tRegion: #{metro[REGION]}\n\n"
      printf "Connected Terminals: \n"
      for list in 0..metro[CONNECTION_LIST].size
        if metro[CONNECTION_LIST][list] != nil
          dest_port = metro[CONNECTION_LIST][list].destination_port
          printf "%s\t", dest_port
          if list + 1 % 8 == 0
           puts
          end
        end
      end

    puts "\n\nTo go back press the enter or input another terminal code\n\n"
      get_input(LIST_COVERAGE)
  end

  def self.get_input(menu)
    run = true
    while run
      run = false
      input = gets.upcase
      if input[0] == "\n" or input == "EXIT\n"
        return input
      else
        self.get_terminal(input.strip)
      end

      case input[0]
        when " "
          exit(0)
        when "1"
          require_relative 'flight_plan'
          flight_plan = Flight_Plan.new(@airline_data)
          flight_plan.prompt_user(@airline_data)
        when "2"
          self.list_coverage
        when "3"
          @airline_data.analyze
          self.stats
        when "4"
          self.generate_map
        when "5"
          self.file_data
        when "E"
          require_relative('edit_data')
          edit = Edit_Data.new()
          edit.get_user_input(@airline_data)
          break
        when "S"
          require_relative('write_data')
          Write_Data.new(@airline_data, "Json/temp.json")
          puts "\n\nFile saved\n\n"
        else
          puts "Please select a valid input"
          run = true
      end
    end
  end

  def self.file_data
    require_relative('parse_info')
    puts "Enter file name:"
    file_name = gets()
    @airline_data.add_data(file_name.strip!)
  end

  def self.get_menu(menu)
    case menu
      when LIST_COVERAGE
        list_coverage
      when MAIN_MENU
        main_menu
      when STATS
        stats
    end
  end

  def self.generate_coordinates_string(coordinate)
    retVal = ""
    if coordinate["N"] != nil
      retVal = "#{coordinate["N"]} degrees North and "
    else if coordinate["S"] != nil
           retVal = "#{coordinate["S"]} degrees South and "
         end
    end

    if coordinate["E"] != nil
      retVal = retVal + "#{coordinate["E"]} degrees East"
    else if coordinate["W"] != nil
           retVal = retVal + "#{coordinate["W"]} degrees West"
         end
    end

  end

  def self.stats()
    puts "CSAir statistics"
    printf "Longest route: %s to %s, %i miles\n", @airline_data.longest_route.departure_port, @airline_data.longest_route.destination_port, @airline_data.longest_route.distance
    printf "Shortest route: %s to %s, %i miles\n", @airline_data.shortest_route.departure_port, @airline_data.shortest_route.destination_port, @airline_data.shortest_route.distance
    printf "Average route length: %i\n\n", @airline_data.average_route_length

    printf "Most populated city reachable: %s, %i\n", @airline_data.population_max[NAME], @airline_data.population_max[POPULATION]
    printf "Least populated city reachable: %s, %i\n", @airline_data.population_min[NAME], @airline_data.population_min[POPULATION]
    printf "Average population size of reachable cities: %i\n", @airline_data.population_average

    self.print_cities_by_connections

    puts "\n\nTo go back press the enter or input another terminal code\n\n"
    get_input(MAIN_MENU)
  end

  def self.print_cities_by_connections
    puts "\nHub Cities:"
    keys = @airline_data.keys
    @airline_data.sort_by_most_connections(keys)
    max = @airline_data.metros[keys[0]][CONNECTION_LIST].size
    for i in 0..keys.size - 1
      metros = @airline_data.metros[keys[i]]
      list = metros[CONNECTION_LIST]
      if i % 5 == 0
        printf("\n")
      end
      if list.size > max - 2
        printf("%-4i (%s) %-16s %-6s", list.size, metros[CODE], metros[NAME], "")
      else
        break
      end
    end
  end


  require_relative('parse_info')
  parse_info = ParseInfo.new('Json/json')
  #parse_info = ParseInfo.new('json')

  require_relative('airline_data')
  @airline_data = AirlineData.new(parse_info.data_sources, parse_info.metros, parse_info.keys)
  @airline_data.metros


=begin
=end
  main_menu
  @airline_data
end